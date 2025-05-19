package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class FeignConnectException extends ExceptionTemplate {
    public FeignConnectException() {
        super(GeneralExceptionMessageKeys.FEIGN_CONNECT_MESSAGE_KEY.getMessageKey());
    }
}
