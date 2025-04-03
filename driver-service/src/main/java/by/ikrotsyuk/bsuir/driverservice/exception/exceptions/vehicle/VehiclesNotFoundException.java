package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class VehiclesNotFoundException extends ExceptionTemplate {
    public VehiclesNotFoundException() {
        super(VehicleExceptionMessageKeys.VEHICLES_NOT_FOUND_MESSAGE_KEY.getMessageKey(), new Object());
    }
}
