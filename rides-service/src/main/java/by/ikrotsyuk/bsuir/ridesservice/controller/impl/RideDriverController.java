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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/driver")
public class RideDriverController implements RideDriverOperations {
    private final RideDriverService rideDriverService;

    @Override
    public ResponseEntity<Page<RideResponseDTO>> getAvailableRides(Long driverId, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(rideDriverService.getAvailableRides(driverId, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RideFullResponseDTO> acceptRide(Long driverId, Long rideId) {
        return new ResponseEntity<>(rideDriverService.acceptRide(driverId, rideId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RideFullResponseDTO> refuseRide(Long driverId, Long rideId) {
        return new ResponseEntity<>(rideDriverService.refuseRide(driverId, rideId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RideFullResponseDTO> beginRide(Long driverId, Long rideId) {
        return new ResponseEntity<>(rideDriverService.beginRide(driverId, rideId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RideFullResponseDTO> endRide(Long driverId, Long rideId) {
        return new ResponseEntity<>(rideDriverService.endRide(driverId, rideId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<RideFullResponseDTO>> getRidesHistory(Long driverId, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(rideDriverService.getRidesHistory(driverId, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RideFullResponseDTO> getCurrentRide(@PathVariable Long driverId) {
        return new ResponseEntity<>(rideDriverService.getCurrentRide(driverId), HttpStatus.OK);
    }
}
