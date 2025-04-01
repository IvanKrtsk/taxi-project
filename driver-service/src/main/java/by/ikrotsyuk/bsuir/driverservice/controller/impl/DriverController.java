package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.DriverOperations;
import by.ikrotsyuk.bsuir.driverservice.service.impl.DriverServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/driver")
public class DriverController implements DriverOperations {
    private final DriverServiceImpl driverService;
    @Override
    public ResponseEntity<?> getDriverProfile(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Double> getDriverRating(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> editDriverProfile(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteDriverProfile(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllDriverVehicles(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getDriverCurrentVehicle(Long id) {
        return null;
    }
}
