package by.ikrotsyuk.bsuir.driverservice.controller;

import by.ikrotsyuk.bsuir.driverservice.dto.DriverRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface DriverOperations {
    ResponseEntity<DriverResponseDTO> getDriverProfile(@PathVariable Long id);
    ResponseEntity<Double> getDriverRating(@PathVariable Long id);
    ResponseEntity<DriverResponseDTO> editDriverProfile(@PathVariable Long id, @RequestBody DriverRequestDTO driverRequestDTO);
    ResponseEntity<DriverResponseDTO> deleteDriverProfile(@PathVariable Long id);
    ResponseEntity<List<DriverResponseDTO>> getAllDrivers();
    ResponseEntity<List<VehicleResponseDTO>> getAllDriverVehicles(@PathVariable Long id);
    ResponseEntity<VehicleResponseDTO> getDriverCurrentVehicle(@PathVariable Long id);
}
