package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class DriversNotFoundException extends ExceptionTemplate {
    public DriversNotFoundException() {
        super(DriverExceptionMessageKeys.DRIVERS_NOT_FOUND_MESSAGE_KEY.getMessageKey(), new Object());
    }
}
