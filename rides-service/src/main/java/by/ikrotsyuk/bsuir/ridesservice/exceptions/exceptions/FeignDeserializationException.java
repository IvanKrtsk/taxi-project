package by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ridesservice.exceptions.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.template.ExceptionTemplate;

public class FeignDeserializationException extends ExceptionTemplate {
    public FeignDeserializationException() {
        super(GeneralExceptionMessageKeys.FEIGN_DESERIALIZATION_MESSAGE_KEY.getMessageKey());
    }
}
