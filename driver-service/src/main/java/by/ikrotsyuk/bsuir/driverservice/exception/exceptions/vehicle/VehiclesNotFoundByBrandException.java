package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class VehiclesNotFoundByBrandException extends ExceptionTemplate {
    public VehiclesNotFoundByBrandException(VehicleExceptionMessageKeys key, String brand) {
        super(key.getMessageKey(), brand);
    }
}
