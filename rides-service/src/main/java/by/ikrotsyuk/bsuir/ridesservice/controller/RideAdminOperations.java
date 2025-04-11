package by.ikrotsyuk.bsuir.ridesservice.controller;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface RideAdminOperations {
    @PatchMapping("/{rideId}")
    ResponseEntity<RideFullResponseDTO> editRide(@PathVariable Long rideId, @Valid @RequestBody RideFullRequestDTO rideFullRequestDTO);

    @DeleteMapping("/{rideId}")
    ResponseEntity<RideFullResponseDTO> deleteRide(@PathVariable Long rideId);

    @GetMapping
    ResponseEntity<Page<RideFullResponseDTO>> getAllRides(@RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);

    @GetMapping("/{rideId}")
    ResponseEntity<RideFullResponseDTO> getRideById(@PathVariable Long rideId);
}
