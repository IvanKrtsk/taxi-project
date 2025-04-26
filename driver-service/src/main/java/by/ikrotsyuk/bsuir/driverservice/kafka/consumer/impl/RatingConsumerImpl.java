package by.ikrotsyuk.bsuir.driverservice.kafka.consumer.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.kafka.NonRetryableException;
import by.ikrotsyuk.bsuir.driverservice.kafka.KafkaConstants;
import by.ikrotsyuk.bsuir.driverservice.kafka.consumer.RatingConsumer;
import by.ikrotsyuk.bsuir.driverservice.service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RatingConsumerImpl implements RatingConsumer {
    private final KafkaService kafkaService;

    @Override
    @KafkaListener(topics = KafkaConstants.TOPIC_NAME)
    public void handleRatingChange(RatingUpdatedEvent event){
        if(!kafkaService.updateRating(event))
            throw new NonRetryableException(KafkaConstants.NON_RETRYABLE_EXCEPTION_TEXT);
    }
}
