package by.ikrotsyuk.bsuir.passengerservice.exception.exceptions;

import by.ikrotsyuk.bsuir.passengerservice.exception.keys.PassengerExceptionMessageKeys;
import by.ikrotsyuk.bsuir.passengerservice.exception.template.ExceptionTemplate;

public class PassengerNotFoundByIdException extends ExceptionTemplate {
    public PassengerNotFoundByIdException(Long id) {
        super(PassengerExceptionMessageKeys.PASSENGER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), id);
    }
}
