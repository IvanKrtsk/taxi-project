package by.ikrotsyuk.bsuir.ridesservice.controller;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface RideAdminOperations {
    ResponseEntity<RideFullResponseDTO> editRide(@RequestParam Long rideId);
    ResponseEntity<RideFullResponseDTO> deleteRide(@RequestParam Long rideId);
    ResponseEntity<Page<RideFullResponseDTO>> getAllRides();
    ResponseEntity<RideFullResponseDTO> getRideById(@RequestParam Long rideId);
}
