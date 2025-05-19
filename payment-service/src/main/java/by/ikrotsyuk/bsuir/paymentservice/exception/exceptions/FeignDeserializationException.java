package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class FeignDeserializationException extends ExceptionTemplate {
    public FeignDeserializationException() {
        super(GeneralExceptionMessageKeys.FEIGN_DESERIALIZATION_MESSAGE_KEY.getMessageKey());
    }
}
