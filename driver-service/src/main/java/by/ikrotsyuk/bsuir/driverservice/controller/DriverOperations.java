package by.ikrotsyuk.bsuir.driverservice.controller;

import by.ikrotsyuk.bsuir.driverservice.dto.DriverRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface DriverOperations {
    ResponseEntity<DriverResponseDTO> getDriverProfile(@PathVariable Long id);
    ResponseEntity<Double> getDriverRating(@PathVariable Long id);
    ResponseEntity<DriverResponseDTO> editDriverProfile(@PathVariable Long id, @RequestBody DriverRequestDTO driverRequestDTO);
    ResponseEntity<DriverResponseDTO> deleteDriverProfile(@PathVariable Long id);
    ResponseEntity<Page<DriverResponseDTO>> getAllDrivers(@RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc);
    ResponseEntity<List<VehicleResponseDTO>> getAllDriverVehicles(@PathVariable Long driverId);
    ResponseEntity<VehicleResponseDTO> getDriverCurrentVehicle(@PathVariable Long driverId);
}
