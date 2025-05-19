package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.promocodes;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.PromoCodesExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class PromoCodeAlreadyExistsException extends ExceptionTemplate {
    public PromoCodeAlreadyExistsException(){
        super(PromoCodesExceptionMessageKeys.PROMO_CODE_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey());
    }
}
