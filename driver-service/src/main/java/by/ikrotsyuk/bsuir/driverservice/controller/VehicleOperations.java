package by.ikrotsyuk.bsuir.driverservice.controller;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface VehicleOperations {
    ResponseEntity<?> addVehicle(@PathVariable Long driverId, @RequestBody VehicleRequestDTO vehicleRequestDTO);
    ResponseEntity<?> editVehicle(@PathVariable Long driverId, @RequestBody VehicleRequestDTO vehicleRequestDTO);
    ResponseEntity<?> makeVehicleCurrent(@PathVariable Long driverId, @PathVariable Long vehicleId);
    ResponseEntity<?> makeVehicleUncurrent(@PathVariable Long driverId, @PathVariable Long vehicleId);
    ResponseEntity<?> getAllVehiclesByType(@PathVariable CarClassTypes type);
    ResponseEntity<?> getAllVehiclesByYear(@PathVariable Integer year);
    ResponseEntity<?> getAllVehiclesByBrand(@PathVariable String brand);
}
