package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class DriverAlreadyDeletedException extends ExceptionTemplate {
    public DriverAlreadyDeletedException(Long id) {
        super(DriverExceptionMessageKeys.DRIVER_ALREADY_DELETED_MESSAGE_KEY.getMessageKey(), id);
    }
}
