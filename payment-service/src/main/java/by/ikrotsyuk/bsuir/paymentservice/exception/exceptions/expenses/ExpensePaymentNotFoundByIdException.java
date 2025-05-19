package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.expenses;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.ExpensesExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;
import org.bson.types.ObjectId;

public class ExpensePaymentNotFoundByIdException extends ExceptionTemplate {
    public ExpensePaymentNotFoundByIdException(ObjectId id){
        super(ExpensesExceptionMessageKeys.EXPENSE_PAYMENT_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), id);
    }
}
