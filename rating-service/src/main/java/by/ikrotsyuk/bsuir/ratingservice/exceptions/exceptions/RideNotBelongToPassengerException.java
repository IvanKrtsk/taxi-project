package by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ratingservice.exceptions.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.template.ExceptionTemplate;

public class RideNotBelongToPassengerException extends ExceptionTemplate {
    public RideNotBelongToPassengerException(Long rideId, Long passengerId) {
        super(GeneralExceptionMessageKeys.RIDE_NOT_BELONG_TO_PASSENGER_MESSAGE_KEY.getMessageKey(), rideId, passengerId);
    }
}
