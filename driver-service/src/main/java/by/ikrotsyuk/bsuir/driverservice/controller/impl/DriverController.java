package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.DriverOperations;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/driver")
public class DriverController implements DriverOperations {
    private final DriverService driverService;

    @Override
    @GetMapping("/profile/{id}")
    public ResponseEntity<DriverResponseDTO> getDriverProfile(@PathVariable Long id) {
        return new ResponseEntity<>(driverService.getDriverProfileById(id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/rating/{id}")
    public ResponseEntity<Double> getDriverRating(@PathVariable Long id) {
        return new ResponseEntity<>(driverService.getDriverRatingById(id), HttpStatus.OK);
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> editDriverProfile(@PathVariable Long id, @Valid @RequestBody DriverRequestDTO driverRequestDTO) {
        return new ResponseEntity<>(driverService.editDriverProfile(id, driverRequestDTO), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<DriverResponseDTO> deleteDriverProfile(@PathVariable Long id) {
        return new ResponseEntity<>(driverService.deleteDriverProfile(id), HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<DriverResponseDTO>> getAllDrivers() {
        return new ResponseEntity<>(driverService.getAllDrivers(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/cars")
    public ResponseEntity<List<VehicleResponseDTO>> getAllDriverVehicles(Long id) {
        return new ResponseEntity<>(driverService.getAllDriverVehicles(id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/car")
    public ResponseEntity<VehicleResponseDTO> getDriverCurrentVehicle(Long id) {
        return new ResponseEntity<>(driverService.getDriverCurrentVehicle(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Boolean> add(@Valid String email){
        return new ResponseEntity<>(driverService.addDriver(email), HttpStatus.CREATED);
    }
}
