package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.expenses;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.ExpensesExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class ExpensePaymentsNotFoundException extends ExceptionTemplate {
    public ExpensePaymentsNotFoundException(){
        super(ExpensesExceptionMessageKeys.EXPENSE_PAYMENTS_NOT_FOUND_MESSAGE_KEY.getMessageKey());
    }
}
