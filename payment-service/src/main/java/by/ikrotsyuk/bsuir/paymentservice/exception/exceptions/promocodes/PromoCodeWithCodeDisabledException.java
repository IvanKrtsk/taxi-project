package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.promocodes;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.PromoCodesExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class PromoCodeWithCodeDisabledException extends ExceptionTemplate {
    public PromoCodeWithCodeDisabledException(String code){
        super(PromoCodesExceptionMessageKeys.PROMO_CODE_WITH_CODE_DISABLED_MESSAGE_KEY.getMessageKey(), code);
    }
}
