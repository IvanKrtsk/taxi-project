package by.ikrotsyuk.bsuir.passengerservice.exception.exceptions;

import by.ikrotsyuk.bsuir.passengerservice.exception.keys.PassengerExceptionMessageKeys;
import by.ikrotsyuk.bsuir.passengerservice.exception.template.ExceptionTemplate;

public class PassengersNotFoundException extends ExceptionTemplate {
    public PassengersNotFoundException() {
        super(PassengerExceptionMessageKeys.PASSENGERS_NOT_FOUND_MESSAGE_KEY.getMessageKey());
    }
}
