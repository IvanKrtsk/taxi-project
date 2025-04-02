package by.ikrotsyuk.bsuir.driverservice.exception.exceptions;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class DriverNotFoundByIdException extends ExceptionTemplate {
    public DriverNotFoundByIdException(DriverExceptionMessageKeys key, Long id) {
        super(key.getMessageKey(), id);
    }
}
