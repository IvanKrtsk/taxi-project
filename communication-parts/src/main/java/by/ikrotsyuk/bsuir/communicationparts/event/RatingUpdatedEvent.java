package by.ikrotsyuk.bsuir.communicationparts.event;

public record RatingUpdatedEvent(
        Long rideId,

        Long reviewerId,

        Long reviewedId,

        Double rating
) {
}
