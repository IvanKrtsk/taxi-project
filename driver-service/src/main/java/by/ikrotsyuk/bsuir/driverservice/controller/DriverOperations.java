package by.ikrotsyuk.bsuir.driverservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface DriverOperations {
    ResponseEntity<?> getDriverProfile(@PathVariable Long id);
    ResponseEntity<Double> getDriverRating(@PathVariable Long id);
    ResponseEntity<?> editDriverProfile(@PathVariable Long id);
    ResponseEntity<?> deleteDriverProfile(@PathVariable Long id);
    ResponseEntity<?> getAllDriverCars(@PathVariable Long id);
    ResponseEntity<?> getDriverCurrentCar(@PathVariable Long id);
}
