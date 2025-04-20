package by.ikrotsyuk.bsuir.ratingservice.kafka.producer;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;
import org.bson.types.ObjectId;

public interface RatingProducer {
    void sendRatingUpdatedEvent(ObjectId reviewId, RatingUpdatedEvent event, ReviewerTypes reviewerType);
    void saveMessageForScheduledSending(ObjectId reviewId, RatingUpdatedEvent event, ReviewerTypes reviewerType, String exceptionMessage);
    void sendUnsentMessages();
}
