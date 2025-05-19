package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.accounts;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.AccountsExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class AccountsNotFoundException extends ExceptionTemplate {
    public AccountsNotFoundException(){
        super(AccountsExceptionMessageKeys.ACCOUNTS_NOT_FOUND_MESSAGE_KEY.getMessageKey());
    }
}
