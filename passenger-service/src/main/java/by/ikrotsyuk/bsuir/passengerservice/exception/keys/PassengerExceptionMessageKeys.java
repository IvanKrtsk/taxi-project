package by.ikrotsyuk.bsuir.passengerservice.exception.keys;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PassengerExceptionMessageKeys {
    PASSENGER_NOT_FOUND_BY_ID_MESSAGE_KEY("passenger.not.found.by.id.message"),
    PASSENGER_NOT_FOUND_BY_EMAIL_MESSAGE_KEY("passenger.not.found.by.email.message"),
    PASSENGER_WITH_SAME_EMAIL_ALREADY_EXISTS_MESSAGE_KEY("passenger.with.same.email.already.exists.message"),
    PASSENGER_WITH_SAME_PHONE_ALREADY_EXISTS_MESSAGE_KEY("passenger.with.same.phone.already.exists.message"),
    PASSENGER_ALREADY_DELETED_MESSAGE_KEY("passenger.already.deleted.message"),
    PASSENGERS_NOT_FOUND_MESSAGE_KEY("passengers.not.found.message");

    private final String messageKey;
}
