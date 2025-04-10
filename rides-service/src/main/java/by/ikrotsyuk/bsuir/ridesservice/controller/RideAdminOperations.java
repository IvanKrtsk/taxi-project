package by.ikrotsyuk.bsuir.ridesservice.controller;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface RideAdminOperations {
    ResponseEntity<RideFullResponseDTO> editRide(@RequestParam Long rideId, @Valid @RequestBody RideFullRequestDTO rideFullRequestDTO);
    ResponseEntity<RideFullResponseDTO> deleteRide(@RequestParam Long rideId);
    ResponseEntity<Page<RideFullResponseDTO>> getAllRides(@RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);
    ResponseEntity<RideFullResponseDTO> getRideById(@RequestParam Long rideId);
}
