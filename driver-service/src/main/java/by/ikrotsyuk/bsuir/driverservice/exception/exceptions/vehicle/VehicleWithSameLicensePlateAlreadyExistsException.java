package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class VehicleWithSameLicensePlateAlreadyExistsException extends ExceptionTemplate {
    public VehicleWithSameLicensePlateAlreadyExistsException(String licensePlate) {
        super(VehicleExceptionMessageKeys.VEHICLE_WITH_SAME_LICENSE_PLATE_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), licensePlate);
    }
}
