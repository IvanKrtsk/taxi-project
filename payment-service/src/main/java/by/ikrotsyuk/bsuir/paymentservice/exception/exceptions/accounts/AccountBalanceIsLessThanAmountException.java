package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.AccountsExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

import java.math.BigDecimal;

public class AccountBalanceIsLessThanAmountException extends ExceptionTemplate {
    public AccountBalanceIsLessThanAmountException(BigDecimal amount){
        super(AccountsExceptionMessageKeys.ACCOUNT_BALANCE_IS_LESS_THAN_AMOUNT.getMessageKey(), amount);
    }
}
