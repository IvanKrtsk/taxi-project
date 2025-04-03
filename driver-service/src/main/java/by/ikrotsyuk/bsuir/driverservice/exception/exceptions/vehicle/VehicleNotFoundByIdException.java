package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class VehicleNotFoundByIdException extends ExceptionTemplate {
    public VehicleNotFoundByIdException(Long vehicleId) {
        super(VehicleExceptionMessageKeys.VEHICLE_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), vehicleId);
    }
}
