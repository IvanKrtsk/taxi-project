package by.ikrotsyuk.bsuir.passengerservice.controller.impl;

import by.ikrotsyuk.bsuir.passengerservice.controller.PassengerOperations;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    public ResponseEntity<PassengerResponseDTO> editPassengerProfile(@PathVariable Long id, @Valid @RequestBody PassengerRequestDTO passengerRequestDTO){
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
    public ResponseEntity<PassengerResponseDTO> addDriver(@NotBlank @Email String email, @NotBlank @Size(max = 15) String phone){
        return new ResponseEntity<>(passengerService.addPassenger(email, phone), HttpStatus.CREATED);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<PassengerResponseDTO>> getAllPassengers(@RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(passengerService.getAllPassengers(offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }
}
