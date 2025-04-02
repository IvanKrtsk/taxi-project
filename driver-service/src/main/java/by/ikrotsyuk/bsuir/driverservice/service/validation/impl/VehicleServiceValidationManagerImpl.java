package by.ikrotsyuk.bsuir.driverservice.service.validation.impl;

import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehicleWithSameLicensePlateAlreadyExistsException;
import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.repository.VehicleRepository;
import by.ikrotsyuk.bsuir.driverservice.service.validation.VehicleServiceValidationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleServiceValidationManagerImpl implements VehicleServiceValidationManager {
    private final VehicleRepository vehicleRepository;
    @Override
    public void checkLicensePlateIsUnique(String licensePlate) {
        if(vehicleRepository.existsByLicensePlate(licensePlate))
            throw new VehicleWithSameLicensePlateAlreadyExistsException(VehicleExceptionMessageKeys.VEHICLE_WITH_SAME_LICENSE_PLATE_ALREADY_EXISTS_MESSAGE_KEY, licensePlate);
    }
}
