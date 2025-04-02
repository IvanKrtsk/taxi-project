package by.ikrotsyuk.bsuir.passengerservice.exception.exceptions;

import by.ikrotsyuk.bsuir.passengerservice.exception.keys.PassengerExceptionMessageKeys;
import by.ikrotsyuk.bsuir.passengerservice.exception.template.ExceptionTemplate;

public class PassengerWithSameEmailAlreadyExistsException extends ExceptionTemplate {
    public PassengerWithSameEmailAlreadyExistsException(PassengerExceptionMessageKeys key, String email) {
        super(key.getMessageKey(), email);
    }
}
