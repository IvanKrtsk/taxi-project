package by.ikrotsyuk.bsuir.passengerservice.controller;

import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface PassengerOperations {
    ResponseEntity<?> getPassengerProfile(@PathVariable Long id);
    ResponseEntity<Double> getPassengerRating(@PathVariable Long id);
    ResponseEntity<?> editPassengerProfile(@PathVariable Long id, @Valid @RequestBody PassengerRequestDTO passengerRequestDTO);
    ResponseEntity<?> deletePassengerProfile(@PathVariable Long id);
}
