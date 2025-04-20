package by.ikrotsyuk.bsuir.driverservice.kafka;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;

public interface RatingConsumer {
    void handleRatingChange(RatingUpdatedEvent event);
}
