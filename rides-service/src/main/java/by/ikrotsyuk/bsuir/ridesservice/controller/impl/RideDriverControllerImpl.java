package by.ikrotsyuk.bsuir.ridesservice.controller.impl;

import by.ikrotsyuk.bsuir.ridesservice.controller.RideDriverOperations;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.service.RideDriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/rides/driver")
public class RideDriverControllerImpl implements RideDriverOperations {
    private final RideDriverService rideDriverService;

    @Override
    @GetMapping("/available/{driverId}")
    public ResponseEntity<Page<RideResponseDTO>> getAvailableRides(@PathVariable Long driverId) {
        return null;
    }

    @Override
    @PatchMapping("/accept/{driverId}")
    public ResponseEntity<RideFullResponseDTO> acceptRide(@PathVariable Long driverId, Long rideId) {
        return null;
    }

    @Override
    @PatchMapping("/refuse/{driverId}")
    public ResponseEntity<RideFullResponseDTO> refuseRide(@PathVariable Long driverId, Long rideId) {
        return null;
    }

    @Override
    @PatchMapping("/begin/{driverId}")
    public ResponseEntity<RideFullResponseDTO> beginRide(@PathVariable Long driverId, Long rideId) {
        return null;
    }

    @Override
    @PatchMapping("/end/{driverId}")
    public ResponseEntity<RideFullResponseDTO> endRide(@PathVariable Long driverId, Long rideId) {
        return null;
    }

    @Override
    @GetMapping("/history/{driverId}")
    public ResponseEntity<Page<RideFullResponseDTO>> getRidesHistory(@PathVariable Long driverId) {
        return null;
    }

    @Override
    @GetMapping("/{driverId}")
    public ResponseEntity<RideFullResponseDTO> getCurrentRide(@PathVariable Long driverId) {
        return null;
    }
}
