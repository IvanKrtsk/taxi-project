package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class DriverVehiclesNotFoundException extends ExceptionTemplate {
    public DriverVehiclesNotFoundException(Long id) {
        super(DriverExceptionMessageKeys.DRIVER_VEHICLES_NOT_FOUND_MESSAGE_KEY.getMessageKey(), id);
    }
}
