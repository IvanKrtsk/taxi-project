package by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ratingservice.exceptions.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.template.ExceptionTemplate;

public class RideNotAcceptedException extends ExceptionTemplate {
    public RideNotAcceptedException(Long rideId) {
        super(GeneralExceptionMessageKeys.RIDE_NOT_ACCEPTED_MESSAGE_KEY.getMessageKey(), rideId);
    }
}
