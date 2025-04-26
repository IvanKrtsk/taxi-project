package by.ikrotsyuk.bsuir.ratingservice.exception.exceptions;

import by.ikrotsyuk.bsuir.ratingservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.exception.template.ExceptionTemplate;

public class ReviewNotFoundByIdException extends ExceptionTemplate {
    public ReviewNotFoundByIdException(String id) {
        super(GeneralExceptionMessageKeys.ID_IS_NOT_VALID_MESSAGE_KEY.getMessageKey(), id);
    }
}
