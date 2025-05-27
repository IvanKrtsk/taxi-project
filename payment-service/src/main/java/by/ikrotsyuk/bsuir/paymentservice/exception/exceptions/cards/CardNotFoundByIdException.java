package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.cards;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.CardsExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class CardNotFoundByIdException extends ExceptionTemplate {
    public CardNotFoundByIdException(Long id){
        super(CardsExceptionMessageKeys.CARD_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), id);
    }
}
