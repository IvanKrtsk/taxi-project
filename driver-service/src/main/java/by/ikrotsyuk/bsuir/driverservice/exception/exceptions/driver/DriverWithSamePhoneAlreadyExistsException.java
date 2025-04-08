package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class DriverWithSamePhoneAlreadyExistsException extends ExceptionTemplate {
    public DriverWithSamePhoneAlreadyExistsException(String phone) {
        super(DriverExceptionMessageKeys.DRIVER_WITH_SAME_PHONE_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), phone);
    }
}
