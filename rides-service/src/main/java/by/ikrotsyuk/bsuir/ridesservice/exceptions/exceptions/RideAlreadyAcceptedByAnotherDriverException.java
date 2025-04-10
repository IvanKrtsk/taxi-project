package by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ridesservice.exceptions.keys.DriverRideExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.template.ExceptionTemplate;

public class RideAlreadyAcceptedByAnotherDriverException extends ExceptionTemplate {
    public RideAlreadyAcceptedByAnotherDriverException(Long id) {
        super(DriverRideExceptionMessageKeys.RIDE_ALREADY_ACCEPTED_BY_ANOTHER_DRIVER_MESSAGE_KEY.name(), id);
    }
}
