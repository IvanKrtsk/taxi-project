package by.ikrotsyuk.bsuir.ridesservice.controller.impl;

import by.ikrotsyuk.bsuir.ridesservice.controller.RidePassengerOperations;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/rides/passenger")
public class RidePassengerControllerImpl implements RidePassengerOperations {

    @Override
    @GetMapping("/{passengerId}")
    public ResponseEntity<BigDecimal> getCostOfRide(@PathVariable Long passengerId, String startLocation, String endLocation) {
        return new ResponseEntity<>(BigDecimal.TEN, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<RideFullResponseDTO>> getRidesStory(Long passengerId) {
        return null;
    }

    @Override
    public ResponseEntity<RideResponseDTO> bookRide(Long passengerId, RideRequestDTO rideRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<RideFullResponseDTO> getCurrentRideInfo(Long passengerId, Long rideId) {
        return null;
    }
}
