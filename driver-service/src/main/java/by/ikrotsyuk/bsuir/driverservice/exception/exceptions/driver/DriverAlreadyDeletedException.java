package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class DriverAlreadyDeletedException extends ExceptionTemplate {
    public DriverAlreadyDeletedException(DriverExceptionMessageKeys key, Long id) {
        super(key.getMessageKey(), id);
    }
}
