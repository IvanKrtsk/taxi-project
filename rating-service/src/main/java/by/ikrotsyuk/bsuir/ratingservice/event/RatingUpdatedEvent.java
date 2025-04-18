package by.ikrotsyuk.bsuir.ratingservice.event;

public record RatingUpdatedEvent(
        Long rideId,

        Long reviewerId,

        Double rating
) {
}
