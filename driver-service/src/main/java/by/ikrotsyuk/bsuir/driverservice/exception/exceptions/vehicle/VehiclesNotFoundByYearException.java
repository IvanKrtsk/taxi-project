package by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle;

import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.template.ExceptionTemplate;

public class VehiclesNotFoundByYearException extends ExceptionTemplate {
    public VehiclesNotFoundByYearException(Integer year) {
        super(VehicleExceptionMessageKeys.VEHICLE_NOT_FOUND_BY_YEAR_MESSAGE_KEY.getMessageKey(), year);
    }
}
