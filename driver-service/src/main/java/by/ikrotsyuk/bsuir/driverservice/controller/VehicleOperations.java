package by.ikrotsyuk.bsuir.driverservice.controller;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface VehicleOperations {
    ResponseEntity<VehicleResponseDTO> addVehicle(@PathVariable Long driverId, @RequestBody VehicleRequestDTO vehicleRequestDTO);
    ResponseEntity<VehicleResponseDTO> editVehicle(@PathVariable Long driverId, @RequestBody VehicleRequestDTO vehicleRequestDTO);
    ResponseEntity<Boolean> makeVehicleCurrent(@PathVariable Long driverId, @PathVariable Long vehicleId);
    ResponseEntity<Boolean> makeVehicleUncurrent(@PathVariable Long driverId, @PathVariable Long vehicleId);
    ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByType(@PathVariable CarClassTypes type);
    ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByYear(@PathVariable Integer year);
    ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByBrand(@PathVariable String brand);
}
