package by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ridesservice.exceptions.keys.PassengerRideExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.template.ExceptionTemplate;

public class RideNotBelongToPassengerException extends ExceptionTemplate {
    public RideNotBelongToPassengerException(Long rideId, Long passengerId) {
        super(PassengerRideExceptionMessageKeys.RIDE_NOT_BELONG_TO_PASSENGER_MESSAGE_KEY.getMessageKey(), rideId, passengerId);
    }
}
