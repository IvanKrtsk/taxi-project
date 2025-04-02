package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.DriverOperations;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    @GetMapping()
    public ResponseEntity<Page<DriverResponseDTO>> getAllDrivers(@RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc) {
        return new ResponseEntity<>(driverService.getAllDrivers(offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @GetMapping("/cars/{driverId}")
    public ResponseEntity<List<VehicleResponseDTO>> getAllDriverVehicles(@PathVariable Long driverId) {
        return new ResponseEntity<>(driverService.getAllDriverVehicles(driverId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/car/{driverId}")
    public ResponseEntity<VehicleResponseDTO> getDriverCurrentVehicle(@PathVariable Long driverId) {
        return new ResponseEntity<>(driverService.getDriverCurrentVehicle(driverId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Boolean> add(@Valid String email){
        return new ResponseEntity<>(driverService.addDriver(email), HttpStatus.CREATED);
    }
}
