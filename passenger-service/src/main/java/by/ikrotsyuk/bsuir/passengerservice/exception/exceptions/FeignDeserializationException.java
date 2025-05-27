package by.ikrotsyuk.bsuir.passengerservice.exception.exceptions;

import by.ikrotsyuk.bsuir.passengerservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.passengerservice.exception.template.ExceptionTemplate;

public class FeignDeserializationException extends ExceptionTemplate {
    public FeignDeserializationException() {
        super(GeneralExceptionMessageKeys.FEIGN_DESERIALIZATION_MESSAGE_KEY.getMessageKey());
    }
}
