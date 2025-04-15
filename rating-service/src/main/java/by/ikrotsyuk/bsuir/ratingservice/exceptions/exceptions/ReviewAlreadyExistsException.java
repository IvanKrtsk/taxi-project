package by.ikrotsyuk.bsuir.ratingservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypeTypes;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.exceptions.template.ExceptionTemplate;

public class ReviewAlreadyExistsException extends ExceptionTemplate {
    public ReviewAlreadyExistsException(Long rideId, ReviewerTypeTypes reviewerTypeTypes) {
        super(GeneralExceptionMessageKeys.REVIEW_FOR_RIDE_BY_REVIEWER_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), rideId, reviewerTypeTypes.name());
    }
}
