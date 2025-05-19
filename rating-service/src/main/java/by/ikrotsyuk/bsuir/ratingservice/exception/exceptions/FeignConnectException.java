package by.ikrotsyuk.bsuir.ratingservice.exception.exceptions;

import by.ikrotsyuk.bsuir.ratingservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.exception.template.ExceptionTemplate;

public class FeignConnectException extends ExceptionTemplate {
    public FeignConnectException() {
        super(GeneralExceptionMessageKeys.FEIGN_CONNECT_MESSAGE_KEY.getMessageKey());
    }
}
