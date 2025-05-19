package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.cards;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.CardsExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class CardAlreadyExistsException extends ExceptionTemplate {
    public CardAlreadyExistsException(String cardNumber){
        super(CardsExceptionMessageKeys.CARD_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), cardNumber);
    }
}
