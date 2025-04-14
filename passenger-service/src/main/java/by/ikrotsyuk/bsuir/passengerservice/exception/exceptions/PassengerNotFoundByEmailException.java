package by.ikrotsyuk.bsuir.passengerservice.exception.exceptions;


import by.ikrotsyuk.bsuir.passengerservice.exception.keys.PassengerExceptionMessageKeys;
import by.ikrotsyuk.bsuir.passengerservice.exception.template.ExceptionTemplate;

public class PassengerNotFoundByEmailException extends ExceptionTemplate {
    public PassengerNotFoundByEmailException(String email) {
        super(PassengerExceptionMessageKeys.PASSENGER_NOT_FOUND_BY_EMAIL_MESSAGE_KEY.getMessageKey(), email);
    }
}
