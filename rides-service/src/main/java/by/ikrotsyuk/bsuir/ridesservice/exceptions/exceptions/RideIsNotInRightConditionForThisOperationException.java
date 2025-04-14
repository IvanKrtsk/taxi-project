package by.ikrotsyuk.bsuir.ridesservice.exceptions.exceptions;

import by.ikrotsyuk.bsuir.ridesservice.entity.customtypes.RideStatusTypes;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ridesservice.exceptions.template.ExceptionTemplate;

public class RideIsNotInRightConditionForThisOperationException extends ExceptionTemplate {
    public RideIsNotInRightConditionForThisOperationException(Long rideId, RideStatusTypes ridesStatus) {
        super(GeneralExceptionMessageKeys.RIDE_IS_NOT_IN_RIGHT_CONDITION_FOR_THIS_OPERATION_MESSAGE_KEY.getMessageKey(), rideId, ridesStatus);
    }
}
