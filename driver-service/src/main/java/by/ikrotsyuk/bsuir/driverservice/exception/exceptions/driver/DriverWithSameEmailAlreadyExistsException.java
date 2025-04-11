package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class DriverWithSameEmailAlreadyExistsException extends ExceptionTemplate {
    public DriverWithSameEmailAlreadyExistsException(String email) {
        super(DriverExceptionMessageKeys.DRIVER_WITH_SAME_EMAIL_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), email);
    }
}
