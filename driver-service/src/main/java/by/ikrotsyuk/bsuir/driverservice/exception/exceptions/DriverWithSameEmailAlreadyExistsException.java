package by.ikrotsyuk.bsuir.driverservice.exception.exceptions;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class DriverWithSameEmailAlreadyExistsException extends ExceptionTemplate {
    public DriverWithSameEmailAlreadyExistsException(DriverExceptionMessageKeys key, String email) {
        super(key.getMessageKey(), email);
    }
}
