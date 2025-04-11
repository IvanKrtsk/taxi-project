package by.ikrotsyuk.bsuir.driverservice.exception.keys;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VehicleExceptionMessageKeys {
    VEHICLE_NOT_FOUND_BY_ID_MESSAGE_KEY("vehicle.not.found.by.id.message"),
    VEHICLE_NOT_BELONG_TO_DRIVER_MESSAGE_KEY("vehicle.not.belong.to.driver.message"),
    VEHICLES_NOT_FOUND_MESSAGE_KEY("vehicles.not.found.message"),
    VEHICLE_NOT_FOUND_BY_LICENSE_PLATE_MESSAGE_KEY("vehicle.not.found.by.license.plate.message"),
    VEHICLE_NOT_FOUND_BY_TYPE_MESSAGE_KEY("vehicle.not.found.by.type.message"),
    VEHICLE_NOT_FOUND_BY_YEAR_MESSAGE_KEY("vehicle.not.found.by.year.message"),
    VEHICLE_NOT_FOUND_BY_BRAND_MESSAGE_KEY("vehicle.not.found.by.brand.message"),
    VEHICLE_WITH_SAME_LICENSE_PLATE_ALREADY_EXISTS_MESSAGE_KEY("vehicle.with.same.license.plate.already.exists.message");

    private final String messageKey;
}
