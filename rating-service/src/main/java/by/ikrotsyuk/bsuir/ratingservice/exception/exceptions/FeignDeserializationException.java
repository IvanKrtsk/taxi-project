package by.ikrotsyuk.bsuir.ratingservice.exception.exceptions;

import by.ikrotsyuk.bsuir.ratingservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.exception.template.ExceptionTemplate;

public class FeignDeserializationException extends ExceptionTemplate {
    public FeignDeserializationException() {
        super(GeneralExceptionMessageKeys.FEIGN_DESERIALIZATION_MESSAGE_KEY.getMessageKey());
    }
}
