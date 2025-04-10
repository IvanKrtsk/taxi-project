package by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ridesservice.exceptions.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.template.ExceptionTemplate;

public class RidesNotFoundException extends ExceptionTemplate {
    public RidesNotFoundException() {
        super(GeneralExceptionMessageKeys.RIDES_NOT_FOUND_MESSAGE_KEY.getMessageKey());
    }
}
