package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.DriverOperations;
import by.ikrotsyuk.bsuir.driverservice.dto.*;
import by.ikrotsyuk.bsuir.driverservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/driver")
public class DriverController implements DriverOperations {
    private final DriverService driverService;

    @Override
    @GetMapping("/profile/{driverId}")
    public ResponseEntity<DriverResponseDTO> getDriverProfile(@PathVariable Long driverId) {
        return new ResponseEntity<>(driverService.getDriverProfileById(driverId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/rating/{driverId}")
    public ResponseEntity<Double> getDriverRating(@PathVariable Long driverId) {
        return new ResponseEntity<>(driverService.getDriverRatingById(driverId), HttpStatus.OK);
    }

    @Override
    @PatchMapping("/{driverId}")
    public ResponseEntity<DriverResponseDTO> editDriverProfile(@PathVariable Long driverId, @Valid @RequestBody DriverRequestDTO driverRequestDTO) {
        return new ResponseEntity<>(driverService.editDriverProfile(driverId, driverRequestDTO), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{driverId}")
    public ResponseEntity<DriverResponseDTO> deleteDriverProfile(@PathVariable Long driverId) {
        return new ResponseEntity<>(driverService.deleteDriverProfile(driverId), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<DriverResponseDTO>> getAllDrivers(@RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(driverService.getAllDrivers(offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DriverResponseDTO> addDriver(@Valid String email){
        return new ResponseEntity<>(driverService.addDriver(email), HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/driverwithvehicles/{driverId}")
    public ResponseEntity<DriverVehicleResponseDTO> getDriverWithVehicle(@PathVariable Long driverId) {
        return new ResponseEntity<>(driverService.getDriverWithVehicleById(driverId), HttpStatus.OK);
    }
}
