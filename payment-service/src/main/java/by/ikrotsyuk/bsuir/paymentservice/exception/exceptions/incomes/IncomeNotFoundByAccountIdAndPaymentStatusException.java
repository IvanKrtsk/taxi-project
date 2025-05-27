package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.incomes;

import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.PaymentStatus;
import by.ikrotsyuk.bsuir.paymentservice.exception.keys.IncomesExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class IncomeNotFoundByAccountIdAndPaymentStatusException extends ExceptionTemplate {
    public IncomeNotFoundByAccountIdAndPaymentStatusException(Long id, PaymentStatus paymentStatus){
        super(IncomesExceptionMessageKeys.INCOME_NOT_FOUND_BY_ACCOUNT_ID_AND_PAYMENT_STATUS_MESSAGE_KEY.getMessageKey(), id, paymentStatus);
    }
}
