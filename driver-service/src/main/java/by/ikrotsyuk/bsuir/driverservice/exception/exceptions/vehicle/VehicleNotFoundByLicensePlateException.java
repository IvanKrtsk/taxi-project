package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class VehicleNotFoundByLicensePlateException extends ExceptionTemplate {
    public VehicleNotFoundByLicensePlateException(VehicleExceptionMessageKeys key, String licensePlate) {
        super(key.getMessageKey(), licensePlate);
    }
}
