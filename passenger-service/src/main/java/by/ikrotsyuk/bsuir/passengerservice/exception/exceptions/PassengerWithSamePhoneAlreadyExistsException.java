package by.ikrotsyuk.bsuir.passengerservice.exception.exceptions;

import by.ikrotsyuk.bsuir.passengerservice.exception.keys.PassengerExceptionMessageKeys;
import by.ikrotsyuk.bsuir.passengerservice.exception.template.ExceptionTemplate;

public class PassengerWithSamePhoneAlreadyExistsException extends ExceptionTemplate {
    public PassengerWithSamePhoneAlreadyExistsException(String email) {
        super(PassengerExceptionMessageKeys.PASSENGER_WITH_SAME_PHONE_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), email);
    }
}
