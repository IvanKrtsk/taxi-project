package by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ridesservice.exceptions.keys.DriverRideExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.template.ExceptionTemplate;

public class AvailableRidesNotFoundException extends ExceptionTemplate {
    public AvailableRidesNotFoundException(Long id) {
        super(DriverRideExceptionMessageKeys.AVAILABLE_RIDES_NOT_FOUND_MESSAGE_KEY.getMessageKey(), id);
    }
}
