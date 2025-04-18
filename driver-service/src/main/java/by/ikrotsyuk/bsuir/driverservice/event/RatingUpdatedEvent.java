package by.ikrotsyuk.bsuir.driverservice.event;

public record RatingUpdatedEvent(
        Long rideId,

        Long reviewerId,

        Double rating
) {
}