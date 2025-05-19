package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.incomes;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.IncomesExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;
import org.bson.types.ObjectId;

public class IncomeNotFoundByIdException extends ExceptionTemplate {
    public IncomeNotFoundByIdException(ObjectId id){
        super(IncomesExceptionMessageKeys.INCOMES_NOT_FOUND_MESSAGE_KEY.getMessageKey(), id);
    }
}
