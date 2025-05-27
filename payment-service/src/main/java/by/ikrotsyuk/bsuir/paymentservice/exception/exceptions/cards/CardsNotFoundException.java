package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.cards;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.CardsExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class CardsNotFoundException extends ExceptionTemplate {
    public CardsNotFoundException(){
        super(CardsExceptionMessageKeys.CARDS_NOT_FOUND_MESSAGE_KEY.getMessageKey());
    }
}
