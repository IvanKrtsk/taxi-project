package by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ratingservice.exceptions.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.template.ExceptionTemplate;

public class ReviewsNotFoundException extends ExceptionTemplate {
    public ReviewsNotFoundException() {
        super(GeneralExceptionMessageKeys.REVIEWS_NOT_FOUND_MESSAGE_KEY.getMessageKey());
    }
}
