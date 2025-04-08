package by.ikrotsyuk.bsuir.driverservice.controller;

import by.ikrotsyuk.bsuir.driverservice.dto.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface DriverOperations {
    ResponseEntity<DriverResponseDTO> getDriverProfile(@PathVariable Long driverId);
    ResponseEntity<Double> getDriverRating(@PathVariable Long driverId);
    ResponseEntity<DriverResponseDTO> editDriverProfile(@PathVariable Long driverId, @Valid @RequestBody DriverRequestDTO driverRequestDTO);
    ResponseEntity<DriverResponseDTO> deleteDriverProfile(@PathVariable Long driverId);
    ResponseEntity<Page<DriverResponseDTO>> getAllDrivers(@RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);
    ResponseEntity<DriverResponseDTO> addDriver(@Valid @RequestBody DriverRequestDTO driverRequestDTO);
    ResponseEntity<DriverVehicleResponseDTO> getDriverWithVehicle(@PathVariable Long driverId);
}
