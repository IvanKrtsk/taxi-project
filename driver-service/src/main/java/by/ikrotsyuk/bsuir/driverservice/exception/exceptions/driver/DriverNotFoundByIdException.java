package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class DriverNotFoundByIdException extends ExceptionTemplate {
    public DriverNotFoundByIdException(Long id) {
        super(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), id);
    }
}
