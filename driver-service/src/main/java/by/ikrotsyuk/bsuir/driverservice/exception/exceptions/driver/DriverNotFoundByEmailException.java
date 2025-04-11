package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class DriverNotFoundByEmailException extends ExceptionTemplate {
    public DriverNotFoundByEmailException(String email) {
        super(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_EMAIL_MESSAGE_KEY.getMessageKey(), email);
    }
}
