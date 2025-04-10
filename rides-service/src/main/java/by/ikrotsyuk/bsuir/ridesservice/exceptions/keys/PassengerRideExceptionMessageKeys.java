package by.ikrotsyuk.bsuir.ridesservice.exceptions.keys;

import lombok.Getter;

@Getter
public enum PassengerRideExceptionMessageKeys {
    RIDE_NOT_BELONG_TO_PASSENGER_MESSAGE_KEY("ride.not.belong.to.passenger.message");

    private final String messageKey;

    PassengerRideExceptionMessageKeys(String messageKey) {
        this.messageKey = messageKey;
    }
}
