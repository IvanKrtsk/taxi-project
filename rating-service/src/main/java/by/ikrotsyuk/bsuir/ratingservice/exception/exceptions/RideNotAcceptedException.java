package by.ikrotsyuk.bsuir.ratingservice.exception.exceptions;

import by.ikrotsyuk.bsuir.ratingservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.exception.template.ExceptionTemplate;

public class RideNotAcceptedException extends ExceptionTemplate {
    public RideNotAcceptedException(Long rideId) {
        super(GeneralExceptionMessageKeys.RIDE_NOT_ACCEPTED_MESSAGE_KEY.getMessageKey(), rideId);
    }
}
