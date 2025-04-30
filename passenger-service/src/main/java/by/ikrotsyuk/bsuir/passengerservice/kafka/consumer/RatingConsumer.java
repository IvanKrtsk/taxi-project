package by.ikrotsyuk.bsuir.passengerservice.kafka.consumer;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;

public interface RatingConsumer {
    void handleRatingChange(RatingUpdatedEvent event);
}
