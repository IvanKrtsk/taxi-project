package by.ikrotsyuk.bsuir.passengerservice.controller.impl;

import by.ikrotsyuk.bsuir.passengerservice.controller.PassengerOperations;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.passengerservice.entity.customtypes.PaymentTypeTypes;
import by.ikrotsyuk.bsuir.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/passenger")
public class PassengerController implements PassengerOperations {
    private final PassengerService passengerService;

    /**
     * id в дальнейшем будет браться из jwt
     */
    @Override
    @GetMapping("/profile/{id}")
    public ResponseEntity<PassengerResponseDTO> getPassengerProfile(@PathVariable Long id) {
        return new ResponseEntity<>(passengerService.getPassengerById(id), HttpStatus.OK);
    }

    /**
     * id в дальнейшем будет браться из jwt
     */
    @Override
    @GetMapping("/rating/{id}")
    public ResponseEntity<Double> getPassengerRating(@PathVariable Long id) {
        return new ResponseEntity<>(passengerService.getPassengerRatingById(id), HttpStatus.OK);
    }

    /**
     * id в дальнейшем будет браться из jwt
     */
    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<PassengerResponseDTO> editPassengerProfile(@PathVariable Long id, PassengerRequestDTO passengerRequestDTO){
        return new ResponseEntity<>(passengerService.editPassengerProfile(id, passengerRequestDTO), HttpStatus.OK);
    }

    /**
     * id в дальнейшем будет браться из jwt
     */
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<PassengerResponseDTO> deletePassengerProfile(@PathVariable Long id) {
        return new ResponseEntity<>(passengerService.deletePassengerProfile(id), HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<PassengerResponseDTO> addPassenger(String email, String phone){
        return new ResponseEntity<>(passengerService.addPassenger(email, phone), HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<PassengerResponseDTO>> getAllPassengers(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(passengerService.getAllPassengers(offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @PatchMapping("/payment/{id}")
    public ResponseEntity<PassengerResponseDTO> changePaymentType(@PathVariable Long id, String paymentType) {
        return new ResponseEntity<>(passengerService.changePaymentType(id, paymentType), HttpStatus.OK);
    }
}
