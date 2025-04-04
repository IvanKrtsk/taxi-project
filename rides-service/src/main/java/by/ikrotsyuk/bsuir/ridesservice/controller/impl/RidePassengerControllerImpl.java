package by.ikrotsyuk.bsuir.ridesservice.controller.impl;

import by.ikrotsyuk.bsuir.ridesservice.controller.RidePassengerOperations;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.service.RidePassengerService;
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
public class RidePassengerControllerImpl implements RidePassengerOperations {
    private final RidePassengerService ridePassengerService;

    @Override
    @GetMapping("/cost/{passengerId}")
    public ResponseEntity<BigDecimal> getCostOfRide(@PathVariable Long passengerId, String startLocation, String endLocation) {
        return new ResponseEntity<>(BigDecimal.TEN, HttpStatus.OK);
    }

    @Override
    @GetMapping("/all/{passengerId}")
    public ResponseEntity<Page<RideFullResponseDTO>> getRidesStory(@PathVariable Long passengerId) {
        return null;
    }

    @Override
    @PostMapping("/{passengerId}")
    public ResponseEntity<RideResponseDTO> bookRide(@PathVariable Long passengerId, RideRequestDTO rideRequestDTO) {
        return null;
    }

    @Override
    @GetMapping("/{passengerId}")
    public ResponseEntity<RideFullResponseDTO> getCurrentRideInfo(@PathVariable Long passengerId, Long rideId) {
        return null;
    }

    @Override
    @GetMapping("ride/{passengerId}")
    public ResponseEntity<RideFullResponseDTO> getRideInfo(@PathVariable Long passengerId, Long rideId) {
        return null;
    }
}
