package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.VehicleOperations;
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
    public ResponseEntity<?> addVehicle() {
        return null;
    }

    @Override
    public ResponseEntity<?> editVehicle() {
        return null;
    }

    @Override
    public ResponseEntity<?> makeVehicleCurrent(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> makeVehicleUncurrent(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllVehiclesByType(String type) {
        return null;
    }
}
