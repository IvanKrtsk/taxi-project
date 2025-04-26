package by.ikrotsyuk.bsuir.driverservice.service;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;

public interface KafkaService {
    boolean updateRating(RatingUpdatedEvent event);
}
