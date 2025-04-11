package by.ikrotsyuk.bsuir.ridesservice.controller.impl;

import by.ikrotsyuk.bsuir.ridesservice.controller.RideOperations;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.service.RideAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/rides")
public class RideController implements RideOperations {
    private final RideAdminService rideAdminService;

    @Override
    public ResponseEntity<RideFullResponseDTO> editRide(Long rideId, RideFullRequestDTO rideFullRequestDTO) {
        return new ResponseEntity<>(rideAdminService.editRide(rideId, rideFullRequestDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RideFullResponseDTO> deleteRide(Long rideId) {
        return new ResponseEntity<>(rideAdminService.deleteRide(rideId), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Page<RideFullResponseDTO>> getAllRides(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(rideAdminService.getAllRides(offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RideFullResponseDTO> getRideById(Long rideId) {
        return new ResponseEntity<>(rideAdminService.getRideById(rideId), HttpStatus.OK);
    }
}
