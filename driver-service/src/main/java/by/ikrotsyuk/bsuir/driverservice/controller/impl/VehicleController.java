package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.VehicleOperations;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.driverservice.service.impl.VehicleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/vehicle")
public class VehicleController implements VehicleOperations {
    private final VehicleServiceImpl vehicleService;

    @Override
    public ResponseEntity<?> addVehicle(Long driverId, VehicleRequestDTO vehicleRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> editVehicle(Long driverId, VehicleRequestDTO vehicleRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> makeVehicleCurrent(Long driverId, Long vehicleId) {
        return null;
    }

    @Override
    public ResponseEntity<?> makeVehicleUncurrent(Long driverId, Long vehicleId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllVehiclesByType(CarClassTypes type) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllVehiclesByYear(Integer year) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllVehiclesByBrand(String brand) {
        return null;
    }
}
