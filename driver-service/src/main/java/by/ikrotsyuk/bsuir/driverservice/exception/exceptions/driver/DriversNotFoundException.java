package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class DriversNotFoundException extends ExceptionTemplate {
    public DriversNotFoundException(DriverExceptionMessageKeys key) {
        super(key.getMessageKey(), new Object());
    }
}
