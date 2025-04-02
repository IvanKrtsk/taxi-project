package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle;

import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class VehiclesNotFoundByTypeException extends ExceptionTemplate {
    public VehiclesNotFoundByTypeException(VehicleExceptionMessageKeys key, CarClassTypes type) {
        super(key.getMessageKey(), type.toString());
    }
}
