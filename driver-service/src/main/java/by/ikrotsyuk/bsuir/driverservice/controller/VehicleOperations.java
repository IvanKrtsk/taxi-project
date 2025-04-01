package by.ikrotsyuk.bsuir.driverservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface VehicleOperations {
    ResponseEntity<?> addVehicle();
    ResponseEntity<?> editVehicle();
    ResponseEntity<?> makeVehicleCurrent(@PathVariable Long id);
    ResponseEntity<?> makeVehicleUncurrent(@PathVariable Long id);
    ResponseEntity<?> getAllVehiclesByType(@PathVariable String type);
}
