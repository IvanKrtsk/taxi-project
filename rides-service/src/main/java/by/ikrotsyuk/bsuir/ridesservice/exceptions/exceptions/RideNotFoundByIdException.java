package by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ridesservice.exceptions.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.template.ExceptionTemplate;

public class RideNotFoundByIdException extends ExceptionTemplate {
    public RideNotFoundByIdException(Long id) {
        super(GeneralExceptionMessageKeys.RIDE_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), id);
    }
}
