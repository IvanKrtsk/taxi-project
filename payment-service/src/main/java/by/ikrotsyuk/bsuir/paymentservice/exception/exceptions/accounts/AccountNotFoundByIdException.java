package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.AccountsExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class AccountNotFoundByIdException extends ExceptionTemplate {
    public AccountNotFoundByIdException(Long accountId){
        super(AccountsExceptionMessageKeys.ACCOUNT_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey());
    }
}
