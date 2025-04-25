package by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ratingservice.exceptions.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.template.ExceptionTemplate;

public class RideNotBelongToDriverException extends ExceptionTemplate {
    public RideNotBelongToDriverException(Long rideId, Long driverId) {
        super(GeneralExceptionMessageKeys.RIDE_NOT_BELONG_TO_DRIVER_MESSAGE_KEY.getMessageKey(), rideId, driverId);
    }
}
