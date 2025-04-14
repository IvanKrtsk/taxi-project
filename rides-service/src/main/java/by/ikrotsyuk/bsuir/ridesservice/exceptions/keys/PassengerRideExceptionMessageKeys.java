package by.ikrotsyuk.bsuir.ridesservice.exceptions.keys;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PassengerRideExceptionMessageKeys {
    RIDE_NOT_BELONG_TO_PASSENGER_MESSAGE_KEY("ride.not.belong.to.passenger.message");

    private final String messageKey;
}
