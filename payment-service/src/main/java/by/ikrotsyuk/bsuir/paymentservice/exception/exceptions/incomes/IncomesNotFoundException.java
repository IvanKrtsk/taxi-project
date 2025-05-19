package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.incomes;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.IncomesExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class IncomesNotFoundException extends ExceptionTemplate {
    public IncomesNotFoundException(){
        super(IncomesExceptionMessageKeys.INCOMES_NOT_FOUND_MESSAGE_KEY.getMessageKey());
    }
}
