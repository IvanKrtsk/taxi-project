package by.ikrotsyuk.bsuir.passengerservice.controller;

import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.passengerservice.entity.customtypes.PaymentTypeTypes;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface PassengerOperations {
    @GetMapping("/{passengerId}/profile")
    ResponseEntity<PassengerResponseDTO> getPassengerProfile(@PathVariable Long passengerId);

    @GetMapping("/{passengerId}/rating")
    ResponseEntity<Double> getPassengerRating(@PathVariable Long passengerId);

    @PutMapping("/{passengerId}")
    ResponseEntity<PassengerResponseDTO> editPassengerProfile(@PathVariable Long passengerId, @Valid @RequestBody PassengerRequestDTO passengerRequestDTO);

    @DeleteMapping("/{passengerId}")
    ResponseEntity<PassengerResponseDTO> deletePassengerProfile(@PathVariable Long passengerId);

    @PostMapping
    ResponseEntity<PassengerResponseDTO> addPassenger(@Valid @RequestBody PassengerRequestDTO passengerRequestDTO);

    @GetMapping
    ResponseEntity<Page<PassengerResponseDTO>> getAllPassengers(@RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);

    @PatchMapping("/{passengerId}")
    ResponseEntity<PassengerResponseDTO> changePaymentType(@PathVariable Long passengerId, @RequestParam @NotNull PaymentTypeTypes paymentType);
}
