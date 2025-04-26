package by.ikrotsyuk.bsuir.ratingservice.exception.exceptions;

import by.ikrotsyuk.bsuir.ratingservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.exception.template.ExceptionTemplate;

public class ReviewsNotFoundException extends ExceptionTemplate {
    public ReviewsNotFoundException() {
        super(GeneralExceptionMessageKeys.REVIEWS_NOT_FOUND_MESSAGE_KEY.getMessageKey());
    }
}
