package by.ikrotsyuk.bsuir.paymentservice.exception.keys;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IncomesExceptionMessageKeys {
    INCOMES_NOT_FOUND_MESSAGE_KEY("incomes.not.found.message"),
    INCOME_NOT_FOUND_BY_ID_MESSAGE_KEY("income.not.found.by.id.message"),
    INCOME_FOR_RIDE_ALREADY_EXISTS_MESSAGE_KEY("income.for.ride.and.account.id.already.exists.message"),
    INCOME_NOT_FOUND_BY_ACCOUNT_ID_AND_PAYMENT_STATUS_MESSAGE_KEY("income.not.found.by.account.id.and.payment.status.message");

    private final String messageKey;
}
