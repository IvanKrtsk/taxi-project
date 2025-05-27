package by.ikrotsyuk.bsuir.driverservice.exception.exceptions;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class FeignDeserializationException extends ExceptionTemplate {
    public FeignDeserializationException() {
        super(GeneralExceptionMessageKeys.FEIGN_DESERIALIZATION_MESSAGE_KEY.getMessageKey());
    }
}
