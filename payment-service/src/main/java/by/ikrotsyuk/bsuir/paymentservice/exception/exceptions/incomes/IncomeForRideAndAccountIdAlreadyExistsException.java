package by.ikrotsyuk.bsuir.paymentservice.exception.exceptions.incomes;

import by.ikrotsyuk.bsuir.paymentservice.exception.keys.IncomesExceptionMessageKeys;
import by.ikrotsyuk.bsuir.paymentservice.exception.template.ExceptionTemplate;

public class IncomeForRideAndAccountIdAlreadyExistsException extends ExceptionTemplate {
    public IncomeForRideAndAccountIdAlreadyExistsException(Long accountId, Long rideId){
        super(IncomesExceptionMessageKeys.INCOME_FOR_RIDE_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), accountId, rideId);
    }
}
