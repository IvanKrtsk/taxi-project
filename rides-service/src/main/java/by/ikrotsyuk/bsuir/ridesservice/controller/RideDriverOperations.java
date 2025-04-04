package by.ikrotsyuk.bsuir.ridesservice.controller;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface RideDriverOperations {
    ResponseEntity<Page<RideResponseDTO>> getAvailableRides(@PathVariable Long driverId, @RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);
    ResponseEntity<RideFullResponseDTO> acceptRide(@PathVariable Long driverId, @RequestParam Long rideId);
    ResponseEntity<RideFullResponseDTO> refuseRide(@PathVariable Long driverId, @RequestParam Long rideId);
    ResponseEntity<RideFullResponseDTO> beginRide(@PathVariable Long driverId, @RequestParam Long rideId);
    ResponseEntity<RideFullResponseDTO> endRide(@PathVariable Long driverId, @RequestParam Long rideId);
    ResponseEntity<Page<RideFullResponseDTO>> getRidesHistory(@PathVariable Long driverId, @RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);
    ResponseEntity<RideFullResponseDTO> getCurrentRide(@PathVariable Long driverId);
}
