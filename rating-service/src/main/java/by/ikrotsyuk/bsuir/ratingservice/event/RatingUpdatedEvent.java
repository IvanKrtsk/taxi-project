package by.ikrotsyuk.bsuir.ratingservice.event;

import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypeTypes;

public record RatingUpdatedEvent(
        Long rideId,

        Long reviewerId,

        Double rating,

        ReviewerTypeTypes reviewerType
) {
}
