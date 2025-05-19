package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts;

import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.exception.keys.AccountsExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class AccountAlreadyExistsException extends ExceptionTemplate {
    public AccountAlreadyExistsException(Long userId, AccountTypes accountType){
        super(AccountsExceptionMessageKeys.ACCOUNT_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), userId, accountType);
    }
}
