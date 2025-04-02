package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class VehicleNotFoundByIdException extends ExceptionTemplate {
    public VehicleNotFoundByIdException(VehicleExceptionMessageKeys key, Long vehicleId) {
        super(key.getMessageKey(), vehicleId);
    }
}
