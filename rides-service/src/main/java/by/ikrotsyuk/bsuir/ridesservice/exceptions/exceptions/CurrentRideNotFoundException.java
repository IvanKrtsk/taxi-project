package by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ridesservice.exceptions.keys.DriverRideExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.template.ExceptionTemplate;

public class CurrentRideNotFoundException extends ExceptionTemplate {
    public CurrentRideNotFoundException(Long id) {
        super(DriverRideExceptionMessageKeys.CURRENT_RIDE_NOT_FOUND_MESSAGE_KEY.getMessageKey(), id);
    }
}
