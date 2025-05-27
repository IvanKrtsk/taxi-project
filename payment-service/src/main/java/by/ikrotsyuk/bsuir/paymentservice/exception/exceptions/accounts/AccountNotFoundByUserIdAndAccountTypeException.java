package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts;

import by.ikrotsyuk.bsuir.paymentservice.entity.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.paymentservice.exception.keys.AccountsExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class AccountNotFoundByUserIdAndAccountTypeException extends ExceptionTemplate {
    public AccountNotFoundByUserIdAndAccountTypeException(Long userId, AccountTypes accountTypes){
        super(AccountsExceptionMessageKeys.ACCOUNT_NOT_FOUND_BY_USER_ID_AND_ACCOUNT_TYPE.getMessageKey(), userId, accountTypes);
    }
}
