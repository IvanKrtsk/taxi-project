package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.promocodes;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.PromoCodesExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class PromoCodeNotFoundByCodeException extends ExceptionTemplate {
    public PromoCodeNotFoundByCodeException(String code){
        super(PromoCodesExceptionMessageKeys.PROMO_CODE_NOT_FOUND_BY_CODE_MESSAGE_KEY.getMessageKey(), code);
    }
}
