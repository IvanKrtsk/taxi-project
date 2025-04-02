package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class DriverNotFoundByEmailException extends ExceptionTemplate {
    public DriverNotFoundByEmailException(DriverExceptionMessageKeys key, String email) {
        super(key.getMessageKey(), email);
    }
}
