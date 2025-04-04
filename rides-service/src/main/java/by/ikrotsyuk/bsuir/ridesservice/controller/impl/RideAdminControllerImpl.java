package by.ikrotsyuk.bsuir.ridesservice.controller.impl;

import by.ikrotsyuk.bsuir.ridesservice.controller.RideAdminOperations;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.service.RideAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/rides/admin")
public class RideAdminControllerImpl implements RideAdminOperations {
    private final RideAdminService rideAdminService;

    @Override
    public ResponseEntity<RideFullResponseDTO> editRide(Long rideId) {
        return null;
    }

    @Override
    public ResponseEntity<RideFullResponseDTO> deleteRide(Long rideId) {
        return null;
    }

    @Override
    public ResponseEntity<Page<RideFullResponseDTO>> getAllRides() {
        return null;
    }

    @Override
    public ResponseEntity<RideFullResponseDTO> getRideById(Long rideId) {
        return null;
    }
}
