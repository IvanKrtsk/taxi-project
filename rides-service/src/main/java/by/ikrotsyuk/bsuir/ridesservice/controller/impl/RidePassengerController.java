package by.ikrotsyuk.bsuir.ridesservice.controller.impl;

import by.ikrotsyuk.bsuir.ridesservice.controller.RidePassengerOperations;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.service.RidePassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/rides/passenger")
public class RidePassengerController implements RidePassengerOperations {
    private final RidePassengerService ridePassengerService;

    @Override
    @GetMapping("/cost/{passengerId}")
    public ResponseEntity<BigDecimal> getCostOfRide(@PathVariable Long passengerId, @Valid RideRequestDTO rideRequestDTO) {
        return new ResponseEntity<>(ridePassengerService.getCostOfRide(passengerId, rideRequestDTO), HttpStatus.OK);
    }

    @Override
    @GetMapping("/all/{passengerId}")
    public ResponseEntity<Page<RideFullResponseDTO>> getRidesStory(@PathVariable Long passengerId, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(ridePassengerService.getRidesStory(passengerId, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @PostMapping("/{passengerId}")
    public ResponseEntity<RideResponseDTO> bookRide(@PathVariable Long passengerId, @Valid @RequestBody RideRequestDTO rideRequestDTO) {
        return new ResponseEntity<>(ridePassengerService.bookRide(passengerId, rideRequestDTO), HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{passengerId}")
    public ResponseEntity<RideFullResponseDTO> getRideInfo(@PathVariable Long passengerId, Long rideId) {
        return new ResponseEntity<>(ridePassengerService.getRideInfo(passengerId, rideId), HttpStatus.OK);
    }

    @Override
    @PatchMapping("/refuse/{passengerId}")
    public ResponseEntity<RideFullResponseDTO> refuseRide(@PathVariable Long passengerId, Long rideId) {
        return new ResponseEntity<>(ridePassengerService.refuseRide(passengerId, rideId), HttpStatus.OK);
    }
}
