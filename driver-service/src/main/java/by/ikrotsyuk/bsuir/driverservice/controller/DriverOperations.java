package by.ikrotsyuk.bsuir.driverservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface DriverOperations {
    ResponseEntity<?> getDriverProfile(@PathVariable Long id);
    ResponseEntity<Double> getDriverRating(@PathVariable Long id);
    ResponseEntity<?> editDriverProfile(@PathVariable Long id);
    ResponseEntity<?> deleteDriverProfile(@PathVariable Long id);
    ResponseEntity<?> getAllDriverVehicles(@PathVariable Long id);
    ResponseEntity<?> getDriverCurrentVehicle(@PathVariable Long id);
}
