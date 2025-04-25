package by.ikrotsyuk.bsuir.ratingservice.exception.exceptions;

import by.ikrotsyuk.bsuir.ratingservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.exception.template.ExceptionTemplate;

public class RideNotBelongToPassengerException extends ExceptionTemplate {
    public RideNotBelongToPassengerException(Long rideId, Long passengerId) {
        super(GeneralExceptionMessageKeys.RIDE_NOT_BELONG_TO_PASSENGER_MESSAGE_KEY.getMessageKey(), rideId, passengerId);
    }
}
