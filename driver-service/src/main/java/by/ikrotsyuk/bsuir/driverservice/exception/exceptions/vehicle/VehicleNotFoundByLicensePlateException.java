package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class VehicleNotFoundByLicensePlateException extends ExceptionTemplate {
    public VehicleNotFoundByLicensePlateException(String licensePlate) {
        super(VehicleExceptionMessageKeys.VEHICLE_NOT_FOUND_BY_LICENSE_PLATE_MESSAGE_KEY.getMessageKey(), licensePlate);
    }
}
