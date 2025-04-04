package by.ikrotsyuk.bsuir.ridesservice.controller;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

public interface RidePassengerOperations {
    ResponseEntity<BigDecimal> getCostOfRide(@PathVariable Long passengerId, @Valid @RequestBody RideRequestDTO rideRequestDTO);
    ResponseEntity<Page<RideFullResponseDTO>> getRidesStory(@PathVariable Long passengerId, @RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);
    ResponseEntity<RideResponseDTO> bookRide(@PathVariable Long passengerId, @Valid @RequestBody RideRequestDTO rideRequestDTO);
    ResponseEntity<RideFullResponseDTO> getCurrentRideInfo(@PathVariable Long passengerId, @RequestParam Long rideId);
    ResponseEntity<RideFullResponseDTO> getRideInfo(@PathVariable Long passengerId, @RequestParam Long rideId);
}
