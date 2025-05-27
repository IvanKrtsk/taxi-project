package by.ikrotsyuk.bsuir.passengerservice.exception.exceptions;


import by.ikrotsyuk.bsuir.passengerservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.passengerservice.exception.template.ExceptionTemplate;

public class FeignConnectException extends ExceptionTemplate {
    public FeignConnectException() {
        super(GeneralExceptionMessageKeys.FEIGN_CONNECT_MESSAGE_KEY.getMessageKey());
    }
}
