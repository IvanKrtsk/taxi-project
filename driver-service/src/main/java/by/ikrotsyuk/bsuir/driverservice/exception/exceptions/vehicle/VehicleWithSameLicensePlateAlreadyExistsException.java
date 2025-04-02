package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class VehicleWithSameLicensePlateAlreadyExistsException extends ExceptionTemplate {
    public VehicleWithSameLicensePlateAlreadyExistsException(VehicleExceptionMessageKeys key, String licensePlate) {
        super(key.getMessageKey(), licensePlate);
    }
}
