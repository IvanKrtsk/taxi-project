package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.expenses;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.ExpensesExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class ExpensePaymentAlreadyExistsException extends ExceptionTemplate {
    public ExpensePaymentAlreadyExistsException(){
        super(ExpensesExceptionMessageKeys.EXPENSE_PAYMENT_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey());
    }
}
