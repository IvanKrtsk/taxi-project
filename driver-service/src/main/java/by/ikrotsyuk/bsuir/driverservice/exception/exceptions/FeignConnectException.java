package by.ikrotsyuk.bsuir.driverservice.exception.exceptions;


import by.ikrotsyuk.bsuir.driverservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class FeignConnectException extends ExceptionTemplate {
    public FeignConnectException() {
        super(GeneralExceptionMessageKeys.FEIGN_CONNECT_MESSAGE_KEY.getMessageKey());
    }
}
