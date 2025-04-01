package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.DriverOperations;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.service.impl.DriverServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/driver")
public class DriverController implements DriverOperations {
    private final DriverServiceImpl driverService;
    @Override
    public ResponseEntity<DriverResponseDTO> getDriverProfile(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Double> getDriverRating(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<DriverResponseDTO> editDriverProfile(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<DriverResponseDTO> deleteDriverProfile(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<VehicleResponseDTO>> getAllDriverVehicles(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<VehicleResponseDTO> getDriverCurrentVehicle(Long id) {
        return null;
    }
}
