package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

import java.util.List;

public class VehicleNotBelongToDriverException extends ExceptionTemplate {
    public VehicleNotBelongToDriverException(Long vehicleId, Long driverId) {
        super(VehicleExceptionMessageKeys.VEHICLE_NOT_BELONG_TO_DRIVER_MESSAGE_KEY.getMessageKey(), List.of(vehicleId, driverId));
    }
}
