package by.ikrotsyuk.bsuir.paymentservice.exception.keys;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccountsExceptionMessageKeys {
    ACCOUNT_ALREADY_EXISTS_MESSAGE_KEY("account.already.exists.message"),
    ACCOUNTS_NOT_FOUND_MESSAGE_KEY("accounts.not.found.message"),
    ACCOUNT_NOT_FOUND_BY_ID_MESSAGE_KEY("account.not.found.by.id.message"),
    ACCOUNT_NOT_FOUND_BY_USER_ID_AND_ACCOUNT_TYPE("account.not.found.by.user.id.and.account.type"),
    ACCOUNT_BALANCE_IS_LESS_THAN_AMOUNT("account.balance.is.less.than.amount");

    private final String messageKey;
}
