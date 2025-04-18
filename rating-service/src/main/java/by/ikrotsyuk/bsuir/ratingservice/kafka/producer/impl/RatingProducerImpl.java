package by.ikrotsyuk.bsuir.ratingservice.kafka.producer.impl;

import by.ikrotsyuk.bsuir.ratingservice.entity.UnsentRatingEntity;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;
import by.ikrotsyuk.bsuir.ratingservice.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.ratingservice.kafka.KafkaAvailabilityChecker;
import by.ikrotsyuk.bsuir.ratingservice.kafka.KafkaConstants;
import by.ikrotsyuk.bsuir.ratingservice.kafka.producer.RatingProducer;
import by.ikrotsyuk.bsuir.ratingservice.mapper.UnsentRatingMapper;
import by.ikrotsyuk.bsuir.ratingservice.repository.UnsentRatingRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Component
public class RatingProducerImpl implements RatingProducer {
    private final KafkaTemplate<String, RatingUpdatedEvent> kafkaTemplate;
    private final UnsentRatingRepository unsentRatingRepository;
    private final UnsentRatingMapper unsentRatingMapper;
    private final KafkaAvailabilityChecker availabilityChecker;
    private final List<UnsentRatingEntity> unsentRatingEntityList = new LinkedList<>();
    private boolean isStartingUp = true;

    @PostConstruct
    void init(){
        availabilityChecker.setRatingProducer(this);
    }

    public void sendRatingUpdatedEvent(ObjectId reviewId, RatingUpdatedEvent event, ReviewerTypes reviewerType){
        if(availabilityChecker.isBrokersAvailable()) {
            if(isStartingUp) {
                sendUnsentMessages();
                isStartingUp = !isStartingUp;
            }
            String topicName = (reviewerType == ReviewerTypes.DRIVER) ? KafkaConstants.DRIVER_RATING_TOPIC_NAME : KafkaConstants.PASSENGER_RATING_TOPIC_NAME;
            CompletableFuture<SendResult<String, RatingUpdatedEvent>> future = kafkaTemplate.send(topicName, reviewId.toString(), event);
            future.whenComplete((result, exception) -> {
                if (exception != null) {
                    saveMessageForScheduledSending(reviewId, event, reviewerType, exception.getMessage());
                    availabilityChecker.setBrokersAvailable(false);
                }
            });
        }else
            saveMessageForScheduledSending(reviewId, event, reviewerType, null);
    }

    public void saveMessageForScheduledSending(ObjectId reviewId, RatingUpdatedEvent event, ReviewerTypes reviewerType, String exceptionMessage){
        UnsentRatingEntity unsentRatingEntity = unsentRatingMapper.toEntity(event, reviewId);
        unsentRatingEntity.setReviewerType(reviewerType);
        if(!Objects.isNull(exceptionMessage))
            unsentRatingEntity.setExceptionMessage(exceptionMessage);
        if (unsentRatingEntityList.stream()
                .noneMatch(item -> item.getId() == unsentRatingEntity.getId()))
            unsentRatingEntityList.add(unsentRatingEntity);
        if (!unsentRatingRepository.existsById(reviewId))
            unsentRatingRepository.save(unsentRatingEntity);
    }

    public void sendUnsentMessages(){
        if(!unsentRatingEntityList.isEmpty()){
            unsentRatingEntityList.forEach(item ->
                sendRatingUpdatedEvent(item.getId(), unsentRatingMapper.toEvent(item), item.getReviewerType())
            );
            unsentRatingRepository.deleteAll(unsentRatingEntityList);
            unsentRatingEntityList.clear();
        }
        List<UnsentRatingEntity> unsentRatingEntities = unsentRatingRepository.findAll();
        if (unsentRatingEntities.isEmpty())
            return;
        unsentRatingEntities.forEach(item -> {
            RatingUpdatedEvent event = unsentRatingMapper.toEvent(item);
            unsentRatingRepository.deleteById(item.getId());
            sendRatingUpdatedEvent(item.getId(), event, item.getReviewerType());
        });
    }
}
