package by.ikrotsyuk.bsuir.passengerservice.controller;

import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.passengerservice.entity.customtypes.PaymentTypeTypesPassenger;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface PassengerOperations {
    ResponseEntity<PassengerResponseDTO> getPassengerProfile(@PathVariable Long id);
    ResponseEntity<Double> getPassengerRating(@PathVariable Long id);
    ResponseEntity<PassengerResponseDTO> editPassengerProfile(@PathVariable Long id, @Valid @RequestBody PassengerRequestDTO passengerRequestDTO);
    ResponseEntity<PassengerResponseDTO> deletePassengerProfile(@PathVariable Long id);
    ResponseEntity<PassengerResponseDTO> addPassenger(@Valid @RequestBody PassengerRequestDTO passengerRequestDTO);
    ResponseEntity<Page<PassengerResponseDTO>> getAllPassengers(@RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);
    ResponseEntity<PassengerResponseDTO> changePaymentType(@PathVariable Long id, @RequestParam @NotBlank PaymentTypeTypesPassenger paymentType);
}
