package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class VehiclesNotFoundByYearException extends ExceptionTemplate {
    public VehiclesNotFoundByYearException(VehicleExceptionMessageKeys key, Integer year) {
        super(key.getMessageKey(), year);
    }
}
