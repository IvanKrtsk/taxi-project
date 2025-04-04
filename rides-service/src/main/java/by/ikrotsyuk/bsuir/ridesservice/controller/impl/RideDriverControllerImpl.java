package by.ikrotsyuk.bsuir.ridesservice.controller.impl;

import by.ikrotsyuk.bsuir.ridesservice.controller.RideDriverOperations;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.service.RideDriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Page<RideResponseDTO>> getAvailableRides(@PathVariable Long driverId, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(rideDriverService.getAvailableRides(driverId, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @PatchMapping("/accept/{driverId}")
    public ResponseEntity<RideFullResponseDTO> acceptRide(@PathVariable Long driverId, Long rideId) {
        return new ResponseEntity<>(rideDriverService.acceptRide(driverId, rideId), HttpStatus.OK);
    }

    @Override
    @PatchMapping("/refuse/{driverId}")
    public ResponseEntity<RideFullResponseDTO> refuseRide(@PathVariable Long driverId, Long rideId) {
        return new ResponseEntity<>(rideDriverService.refuseRide(driverId, rideId), HttpStatus.OK);
    }

    @Override
    @PatchMapping("/begin/{driverId}")
    public ResponseEntity<RideFullResponseDTO> beginRide(@PathVariable Long driverId, Long rideId) {
        return new ResponseEntity<>(rideDriverService.beginRide(driverId, rideId), HttpStatus.OK);
    }

    @Override
    @PatchMapping("/end/{driverId}")
    public ResponseEntity<RideFullResponseDTO> endRide(@PathVariable Long driverId, Long rideId) {
        return new ResponseEntity<>(rideDriverService.endRide(driverId, rideId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/history/{driverId}")
    public ResponseEntity<Page<RideFullResponseDTO>> getRidesHistory(@PathVariable Long driverId, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(rideDriverService.getRidesHistory(driverId, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{driverId}")
    public ResponseEntity<RideFullResponseDTO> getCurrentRide(@PathVariable Long driverId) {
        return new ResponseEntity<>(rideDriverService.getCurrentRide(driverId), HttpStatus.OK);
    }
}
