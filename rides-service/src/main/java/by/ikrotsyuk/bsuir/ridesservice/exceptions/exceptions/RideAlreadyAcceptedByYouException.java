package by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ridesservice.exceptions.keys.DriverRideExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.template.ExceptionTemplate;

public class RideAlreadyAcceptedByYouException extends ExceptionTemplate {
    public RideAlreadyAcceptedByYouException(Long id) {
        super(DriverRideExceptionMessageKeys.RIDE_ALREADY_ACCEPTED_BY_YOU_MESSAGE_KEY.getMessageKey(), id);
    }
}
