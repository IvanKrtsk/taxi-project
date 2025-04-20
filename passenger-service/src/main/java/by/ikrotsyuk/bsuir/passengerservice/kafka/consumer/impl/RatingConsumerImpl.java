package by.ikrotsyuk.bsuir.passengerservice.kafka.consumer.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.kafka.NonRetryableException;
import by.ikrotsyuk.bsuir.passengerservice.kafka.KafkaConstants;
import by.ikrotsyuk.bsuir.passengerservice.kafka.consumer.RatingConsumer;
import by.ikrotsyuk.bsuir.passengerservice.service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RatingConsumerImpl implements RatingConsumer {
    private final KafkaService kafkaService;
    @Override
    public void handleRatingChange(RatingUpdatedEvent event) {
        if(!kafkaService.updateRating(event))
            throw new NonRetryableException(KafkaConstants.NON_RETRYABLE_EXCEPTION_TEXT);
    }
}
