package by.ikrotsyuk.bsuir.passengerservice.controller.impl;

import by.ikrotsyuk.bsuir.passengerservice.controller.PassengerOperations;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/passenger")
public class PassengerController implements PassengerOperations {
    private final PassengerService passengerService;

    /**
     * id в дальнейшем будет браться из jwt
     */
    @Override
    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getPassengerProfile(@PathVariable Long id) {
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
    @PatchMapping("/edit/{id}")
    public ResponseEntity<?> editPassengerProfile(@PathVariable Long id, @Valid @RequestBody PassengerRequestDTO passengerRequestDTO){
        return new ResponseEntity<>(passengerService.editPassengerProfile(id, passengerRequestDTO), HttpStatus.OK);
    }

    /**
     * id в дальнейшем будет браться из jwt
     */
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePassengerProfile(@PathVariable Long id) {
        return new ResponseEntity<>(passengerService.deletePassengerProfile(id), HttpStatus.OK);
    }
}
