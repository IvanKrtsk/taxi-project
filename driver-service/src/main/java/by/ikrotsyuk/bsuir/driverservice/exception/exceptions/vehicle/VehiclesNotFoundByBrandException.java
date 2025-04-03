package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class VehiclesNotFoundByBrandException extends ExceptionTemplate {
    public VehiclesNotFoundByBrandException(String brand) {
        super(VehicleExceptionMessageKeys.VEHICLE_NOT_FOUND_BY_BRAND_MESSAGE_KEY.getMessageKey(), brand);
    }
}
