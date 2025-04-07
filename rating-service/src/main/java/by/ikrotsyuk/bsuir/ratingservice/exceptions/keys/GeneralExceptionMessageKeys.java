package by.ikrotsyuk.bsuir.ratingservice.exceptions.keys;

import lombok.Getter;

@Getter
public enum GeneralExceptionMessageKeys {
    REVIEW_NOT_FOUND_BY_ID_MESSAGE_KEY("review.not.found.by.id.message"),
    REVIEWS_NOT_FOUND_MESSAGE_KEY("reviews.not.found.message"),
    ID_IS_NOT_VALID_MESSAGE_KEY("id.is.not.valid.message");

    private final String messageKey;

    GeneralExceptionMessageKeys(String messageKey) {
        this.messageKey = messageKey;
    }
}
