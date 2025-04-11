package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class DriverCurrentVehicleNotFoundException extends ExceptionTemplate {
    public DriverCurrentVehicleNotFoundException(Long driverId) {
        super(DriverExceptionMessageKeys.DRIVER_CURRENT_VEHICLE_NOT_FOUND.getMessageKey(), driverId);
    }
}
