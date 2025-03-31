package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.VehicleOperations;
import org.springframework.http.ResponseEntity;

public class VehicleController implements VehicleOperations {
    @Override
    public ResponseEntity<?> addVehicle() {
        return null;
    }

    @Override
    public ResponseEntity<?> editVehicle() {
        return null;
    }

    @Override
    public ResponseEntity<?> setVehicleCurrent(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> setVehicleNotCurrent(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllVehiclesByType(String type) {
        return null;
    }
}
