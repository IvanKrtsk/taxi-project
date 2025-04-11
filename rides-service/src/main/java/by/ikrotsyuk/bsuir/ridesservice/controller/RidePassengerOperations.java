package by.ikrotsyuk.bsuir.ridesservice.controller;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

public interface RidePassengerOperations {
    @GetMapping("/cost/{passengerId}")
    ResponseEntity<BigDecimal> getCostOfRide(@PathVariable Long passengerId, @Valid RideRequestDTO rideRequestDTO);

    @GetMapping("/all/{passengerId}")
    ResponseEntity<Page<RideFullResponseDTO>> getRidesStory(@PathVariable Long passengerId, @RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);

    @PostMapping("/{passengerId}")
    ResponseEntity<RideResponseDTO> bookRide(@PathVariable Long passengerId, @Valid @RequestBody RideRequestDTO rideRequestDTO);

    @GetMapping("/{passengerId}")
    ResponseEntity<RideFullResponseDTO> getRideInfo(@PathVariable Long passengerId, @RequestParam Long rideId);

    @PatchMapping("/refuse/{passengerId}")
    ResponseEntity<RideFullResponseDTO> refuseRide(@PathVariable Long passengerId, @RequestParam Long rideId);
}
