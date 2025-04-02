package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

import java.util.List;

public class VehicleNotBelongToDriverException extends ExceptionTemplate {
    public VehicleNotBelongToDriverException(VehicleExceptionMessageKeys key, Long vehicleId, Long driverId) {
        super(key.getMessageKey(), List.of(vehicleId, driverId));
    }
}
