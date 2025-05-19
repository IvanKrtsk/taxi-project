package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.promocodes;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.PromoCodesExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class PromoCodesNotFoundException extends ExceptionTemplate {
    public PromoCodesNotFoundException(){
        super(PromoCodesExceptionMessageKeys.PROMO_CODES_NOT_FOUND_MESSAGE_KEY.getMessageKey());
    }
}
