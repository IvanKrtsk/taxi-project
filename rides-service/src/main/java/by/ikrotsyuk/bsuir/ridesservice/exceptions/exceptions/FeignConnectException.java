package by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions;


import by.ikrotsyuk.bsuir.ridesservice.exceptions.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.template.ExceptionTemplate;

public class FeignConnectException extends ExceptionTemplate {
    public FeignConnectException() {
        super(GeneralExceptionMessageKeys.FEIGN_CONNECT_MESSAGE_KEY.getMessageKey());
    }
}
