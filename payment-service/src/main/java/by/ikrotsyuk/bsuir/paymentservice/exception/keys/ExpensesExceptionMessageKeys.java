package by.ikrotsyuk.bsuir.paymentservice.exception.keys;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExpensesExceptionMessageKeys {
    EXPENSE_PAYMENT_ALREADY_EXISTS_MESSAGE_KEY("expense.payment.already.exists.message"),
    EXPENSE_PAYMENT_NOT_FOUND_BY_ID_MESSAGE_KEY("expense.payment.not.found.by.id.message"),
    EXPENSE_PAYMENTS_NOT_FOUND_MESSAGE_KEY("expense.payments.not.found.message");

    private final String messageKey;
}
