package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle;

import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class VehiclesNotFoundByTypeException extends ExceptionTemplate {
    public VehiclesNotFoundByTypeException(CarClassTypes type) {
        super(VehicleExceptionMessageKeys.VEHICLE_NOT_FOUND_BY_TYPE_MESSAGE_KEY.getMessageKey(), type.toString());
    }
}
