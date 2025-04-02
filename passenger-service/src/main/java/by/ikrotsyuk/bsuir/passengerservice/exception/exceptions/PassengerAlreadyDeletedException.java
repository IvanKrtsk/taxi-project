package by.ikrotsyuk.bsuir.passengerservice.exception.exceptions;

import by.ikrotsyuk.bsuir.passengerservice.exception.keys.PassengerExceptionMessageKeys;
import by.ikrotsyuk.bsuir.passengerservice.exception.template.ExceptionTemplate;

public class PassengerAlreadyDeletedException extends ExceptionTemplate{
    public PassengerAlreadyDeletedException(PassengerExceptionMessageKeys key, Long id) {
        super(key.getMessageKey(), id);
    }
}
