package by.ikrotsyuk.bsuir.driverservice.exception.exceptions;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class DriverWithSamePhoneAlreadyExistsException extends ExceptionTemplate {
    public DriverWithSamePhoneAlreadyExistsException(DriverExceptionMessageKeys key, String phone) {
        super(key.getMessageKey(), phone);
    }
}
