package by.ikrotsyuk.bsuir.ridesservice.exceptions.keys;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DriverRideExceptionMessageKeys {
    AVAILABLE_RIDES_NOT_FOUND_MESSAGE_KEY("available.rides.not.found.message"),
    RIDE_ALREADY_ACCEPTED_BY_ANOTHER_DRIVER_MESSAGE_KEY("ride.already.accepted.by.another.driver.message"),
    RIDE_ALREADY_ACCEPTED_BY_YOU_MESSAGE_KEY("ride.already.accepted.by.you.message"),
    RIDE_NOT_BELONG_TO_DRIVER_MESSAGE_KEY("ride.not.belong.to.driver.message"),
    CURRENT_RIDE_NOT_FOUND_MESSAGE_KEY("current.ride.not.found.message");

    private final String messageKey;
}
