package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.promocodes;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.PromoCodesExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class PromoCodeNotFoundByIdMessage extends ExceptionTemplate {
    public PromoCodeNotFoundByIdMessage(Long id){
        super(PromoCodesExceptionMessageKeys.PROMO_CODE_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), id);
    }
}
