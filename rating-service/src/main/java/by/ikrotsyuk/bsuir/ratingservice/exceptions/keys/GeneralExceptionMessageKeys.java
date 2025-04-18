package by.ikrotsyuk.bsuir.ratingservice.exceptions.keys;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GeneralExceptionMessageKeys {
    REVIEW_NOT_FOUND_BY_ID_MESSAGE_KEY("review.not.found.by.id.message"),
    REVIEWS_NOT_FOUND_MESSAGE_KEY("reviews.not.found.message"),
    ID_IS_NOT_VALID_MESSAGE_KEY("id.is.not.valid.message"),
    METHOD_ARGUMENT_TYPE_MISMATCH_MESSAGE_KEY("method.argument.type.mismatch.message"),
    ENUM_ARGUMENT_DESERIALIZATION_MESSAGE_KEY("enum.argument.deserialization.message"),
    FIELD_DESERIALIZATION_MESSAGE_KEY("field.deserialization.message"),
    REVIEW_FOR_RIDE_BY_REVIEWER_ALREADY_EXISTS_MESSAGE_KEY("review.for.ride.by.reviewer.already.exists.message");

    private final String messageKey;
}
