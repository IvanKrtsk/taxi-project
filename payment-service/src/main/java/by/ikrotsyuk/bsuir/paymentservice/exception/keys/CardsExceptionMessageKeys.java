package by.ikrotsyuk.bsuir.paymentservice.exception.keys;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardsExceptionMessageKeys {
    CARD_NOT_FOUND_BY_ID_MESSAGE_KEY("card.not.found.by.id.message"),
    CARDS_NOT_FOUND_MESSAGE_KEY("cards.not.found.message"),
    CARD_ALREADY_EXISTS_MESSAGE_KEY("card.already.exists.message");

    private final String messageKey;
}
