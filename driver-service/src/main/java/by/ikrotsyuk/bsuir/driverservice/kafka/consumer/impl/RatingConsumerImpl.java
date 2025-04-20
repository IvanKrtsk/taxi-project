package by.ikrotsyuk.bsuir.driverservice.kafka.consumer.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.kafka.NonRetrybleException;
import by.ikrotsyuk.bsuir.driverservice.kafka.KafkaConstants;
import by.ikrotsyuk.bsuir.driverservice.service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RatingConsumerImpl {
    private final KafkaService kafkaService;

    @KafkaListener(topics = KafkaConstants.TOPIC_NAME)
    public void handleRatingChange(RatingUpdatedEvent event){
        if(!kafkaService.updateRating(event))
            throw new NonRetrybleException(KafkaConstants.NON_RETRYABLE_EXCEPTION_TEXT);
    }
}
