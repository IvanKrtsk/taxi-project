package by.ikrotsyuk.bsuir.ridesservice.controller;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface RideDriverOperations {
    @GetMapping("/available/{driverId}")
    ResponseEntity<Page<RideResponseDTO>> getAvailableRides(@PathVariable Long driverId, @RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);

    @PatchMapping("/accept/{driverId}")
    ResponseEntity<RideFullResponseDTO> acceptRide(@PathVariable Long driverId, @RequestParam Long rideId);

    @PatchMapping("/refuse/{driverId}")
    ResponseEntity<RideFullResponseDTO> refuseRide(@PathVariable Long driverId, @RequestParam Long rideId);

    @PatchMapping("/begin/{driverId}")
    ResponseEntity<RideFullResponseDTO> beginRide(@PathVariable Long driverId, @RequestParam Long rideId);

    @PatchMapping("/end/{driverId}")
    ResponseEntity<RideFullResponseDTO> endRide(@PathVariable Long driverId, @RequestParam Long rideId);

    @GetMapping("/all/{driverId}")
    ResponseEntity<Page<RideFullResponseDTO>> getRidesHistory(@PathVariable Long driverId, @RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);

    @GetMapping("/{driverId}")
    ResponseEntity<RideFullResponseDTO> getCurrentRide(@PathVariable Long driverId);
}
