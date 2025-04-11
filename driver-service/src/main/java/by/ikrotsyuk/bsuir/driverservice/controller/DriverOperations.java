package by.ikrotsyuk.bsuir.driverservice.controller;

import by.ikrotsyuk.bsuir.driverservice.dto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface DriverOperations {
    @GetMapping("/profile/{driverId}")
    ResponseEntity<DriverResponseDTO> getDriverProfile(@PathVariable Long driverId);

    @GetMapping("/rating/{driverId}")
    ResponseEntity<Double> getDriverRating(@PathVariable Long driverId);

    @PutMapping("/{driverId}")
    ResponseEntity<DriverResponseDTO> editDriverProfile(@PathVariable Long driverId, @Valid @RequestBody DriverRequestDTO driverRequestDTO);

    @DeleteMapping("/{driverId}")
    ResponseEntity<DriverResponseDTO> deleteDriverProfile(@PathVariable Long driverId);

    @GetMapping
    ResponseEntity<Page<DriverResponseDTO>> getAllDrivers(@RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);

    @PostMapping
    ResponseEntity<DriverResponseDTO> addDriver(@Valid @RequestBody DriverRequestDTO driverRequestDTO);

    @GetMapping("/driver-with-vehicles/{driverId}")
    ResponseEntity<DriverVehicleResponseDTO> getDriverWithVehicle(@PathVariable Long driverId);
}
