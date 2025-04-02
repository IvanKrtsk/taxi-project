package by.ikrotsyuk.bsuir.driverservice.exception.keys;

import lombok.Getter;

@Getter
public enum DriverExceptionMessageKeys {
    DRIVER_NOT_FOUND_BY_ID_MESSAGE_KEY("driver.not.found.by.id.message"),
    DRIVER_ALREADY_DELETED_MESSAGE_KEY("driver.already.deleted.message"),
    DRIVER_NOT_FOUND_BY_EMAIL_MESSAGE_KEY("driver.not.found.by.email.message"),
    DRIVERS_NOT_FOUND_MESSAGE_KEY("drivers.not.found.message"),
    DRIVER_VEHICLES_NOT_FOUND_MESSAGE_KEY("driver.vehicles.not.found.message"),
    DRIVER_WITH_SAME_EMAIL_ALREADY_EXISTS_MESSAGE_KEY("driver.with.same.email.already.exists.message"),
    DRIVER_WITH_SAME_PHONE_ALREADY_EXISTS_MESSAGE_KEY("driver.with.same.phone.already.exists.message"),
    DRIVER_CURRENT_VEHICLE_NOT_FOUND("driver.current.vehicle.not.found");

    private final String messageKey;

    DriverExceptionMessageKeys(String messageKey) {
        this.messageKey = messageKey;
    }
}