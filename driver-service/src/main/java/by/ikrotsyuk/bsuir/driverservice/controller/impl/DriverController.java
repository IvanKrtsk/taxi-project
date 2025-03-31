package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.DriverOperations;
import org.springframework.http.ResponseEntity;

public class DriverController implements DriverOperations {
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
    public ResponseEntity<?> getAllDriverCars(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getDriverCurrentCar(Long id) {
        return null;
    }
}
