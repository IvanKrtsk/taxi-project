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
@RequestMapping("/api/v1/passengers")
public class PassengerController implements PassengerOperations {
    private final PassengerService passengerService;

    /**
     * id в дальнейшем будет браться из jwt
     */
    @Override
    public ResponseEntity<PassengerResponseDTO> getPassengerProfile(Long passengerId) {
        return new ResponseEntity<>(passengerService.getPassengerById(passengerId), HttpStatus.OK);
    }

    /**
     * id в дальнейшем будет браться из jwt
     */
    @Override
    public ResponseEntity<Double> getPassengerRating(Long passengerId) {
        return new ResponseEntity<>(passengerService.getPassengerRatingById(passengerId), HttpStatus.OK);
    }

    /**
     * id в дальнейшем будет браться из jwt
     */
    @Override
    public ResponseEntity<PassengerResponseDTO> editPassengerProfile(Long passengerId, PassengerRequestDTO passengerRequestDTO){
        return new ResponseEntity<>(passengerService.editPassengerProfile(passengerId, passengerRequestDTO), HttpStatus.OK);
    }

    /**
     * id в дальнейшем будет браться из jwt
     */
    @Override
    public ResponseEntity<PassengerResponseDTO> deletePassengerProfile(Long passengerId) {
        return new ResponseEntity<>(passengerService.deletePassengerProfile(passengerId), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<PassengerResponseDTO> addPassenger(PassengerRequestDTO passengerRequestDTO){
        return new ResponseEntity<>(passengerService.addPassenger(passengerRequestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Page<PassengerResponseDTO>> getAllPassengers(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(passengerService.getAllPassengers(offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PassengerResponseDTO> changePaymentType(Long passengerId, PaymentTypeTypes paymentType) {
        return new ResponseEntity<>(passengerService.changePaymentType(passengerId, paymentType), HttpStatus.OK);
    }
}
