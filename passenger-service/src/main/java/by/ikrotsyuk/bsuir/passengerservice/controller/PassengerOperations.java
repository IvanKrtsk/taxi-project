package by.ikrotsyuk.bsuir.passengerservice.controller;

import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface PassengerOperations {
    ResponseEntity<PassengerResponseDTO> getPassengerProfile(@PathVariable Long id);
    ResponseEntity<Double> getPassengerRating(@PathVariable Long id);
    ResponseEntity<PassengerResponseDTO> editPassengerProfile(@PathVariable Long id, @Valid @RequestBody PassengerRequestDTO passengerRequestDTO);
    ResponseEntity<PassengerResponseDTO> deletePassengerProfile(@PathVariable Long id);
    ResponseEntity<PassengerResponseDTO> addDriver(@Valid String email);
}
