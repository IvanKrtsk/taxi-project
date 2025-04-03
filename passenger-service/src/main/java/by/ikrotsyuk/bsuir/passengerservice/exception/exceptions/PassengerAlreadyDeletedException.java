package by.ikrotsyuk.bsuir.passengerservice.exception.exceptions;

import by.ikrotsyuk.bsuir.passengerservice.exception.keys.PassengerExceptionMessageKeys;
import by.ikrotsyuk.bsuir.passengerservice.exception.template.ExceptionTemplate;

public class PassengerAlreadyDeletedException extends ExceptionTemplate{
    public PassengerAlreadyDeletedException(Long id) {
        super(PassengerExceptionMessageKeys.PASSENGER_ALREADY_DELETED_MESSAGE_KEY.getMessageKey(), id);
    }
}
