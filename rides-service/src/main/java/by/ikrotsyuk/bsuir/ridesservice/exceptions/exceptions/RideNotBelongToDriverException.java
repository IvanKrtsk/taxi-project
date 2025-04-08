package by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ridesservice.exceptions.keys.DriverRideExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.template.ExceptionTemplate;

public class RideNotBelongToDriverException extends ExceptionTemplate {
    public RideNotBelongToDriverException(Long rideId, Long driverId) {
        super(DriverRideExceptionMessageKeys.RIDE_NOT_BELONG_TO_DRIVER_MESSAGE_KEY.getMessageKey(), rideId, driverId);
    }
}
