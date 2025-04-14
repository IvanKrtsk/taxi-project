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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/passenger")
public class RidePassengerController implements RidePassengerOperations {
    private final RidePassengerService ridePassengerService;

    @Override
    public ResponseEntity<BigDecimal> getCostOfRide(Long passengerId, RideRequestDTO rideRequestDTO) {
        return new ResponseEntity<>(ridePassengerService.getCostOfRide(passengerId, rideRequestDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<RideFullResponseDTO>> getRidesStory(Long passengerId, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(ridePassengerService.getRidesStory(passengerId, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RideResponseDTO> bookRide(Long passengerId, RideRequestDTO rideRequestDTO) {
        return new ResponseEntity<>(ridePassengerService.bookRide(passengerId, rideRequestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<RideFullResponseDTO> getRideInfo(Long passengerId, Long rideId) {
        return new ResponseEntity<>(ridePassengerService.getRideInfo(passengerId, rideId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RideFullResponseDTO> refuseRide(Long passengerId, Long rideId) {
        return new ResponseEntity<>(ridePassengerService.cancelRide(passengerId, rideId), HttpStatus.OK);
    }
}
