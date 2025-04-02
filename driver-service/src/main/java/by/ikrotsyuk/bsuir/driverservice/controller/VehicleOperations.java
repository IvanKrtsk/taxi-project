package by.ikrotsyuk.bsuir.driverservice.controller;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface VehicleOperations {
    ResponseEntity<VehicleResponseDTO> addVehicle(@PathVariable Long driverId, @RequestBody VehicleRequestDTO vehicleRequestDTO);
    ResponseEntity<VehicleResponseDTO> editVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId, @RequestBody VehicleRequestDTO vehicleRequestDTO);
    ResponseEntity<VehicleResponseDTO> chooseCurrentVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId);
    ResponseEntity<List<VehicleResponseDTO>> getAllVehicles();
    ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long vehicleId);
    ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByType(@RequestParam CarClassTypes type);
    ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByYear(@RequestParam Integer year);
    ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByBrand(@RequestParam String brand);
}
