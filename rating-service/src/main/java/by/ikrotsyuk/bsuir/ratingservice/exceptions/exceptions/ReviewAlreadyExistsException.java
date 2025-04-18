package by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.template.ExceptionTemplate;

public class ReviewAlreadyExistsException extends ExceptionTemplate {
    public ReviewAlreadyExistsException(Long rideId, ReviewerTypes reviewerTypes) {
        super(GeneralExceptionMessageKeys.REVIEW_FOR_RIDE_BY_REVIEWER_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), rideId, reviewerTypes.name());
    }
}
