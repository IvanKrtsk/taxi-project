package by.ikrotsyuk.bsuir.paymentservice.exception.keys;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PromoCodesExceptionMessageKeys {
    PROMO_CODES_NOT_FOUND_MESSAGE_KEY("promocodes.not.found.message"),
    PROMO_CODE_NOT_FOUND_BY_ID_MESSAGE_KEY("promocode.not.found.by.id.message"),
    PROMO_CODE_ALREADY_EXISTS_MESSAGE_KEY("promocode.already.exists.message"),
    PROMO_CODE_NOT_FOUND_BY_CODE_MESSAGE_KEY("promocode.not.found.by.code.message"),
    PROMO_CODE_WITH_CODE_DISABLED_MESSAGE_KEY("promocode.with.code.disabled.message");

    private final String messageKey;
}
