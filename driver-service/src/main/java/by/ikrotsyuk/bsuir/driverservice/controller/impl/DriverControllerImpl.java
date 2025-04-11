package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.DriverOperations;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverVehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/drivers")
public class DriverControllerImpl implements DriverOperations {
    private final DriverService driverService;

    @Override
    public ResponseEntity<DriverResponseDTO> getDriverProfile(Long driverId) {
        return new ResponseEntity<>(driverService.getDriverProfileById(driverId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Double> getDriverRating(Long driverId) {
        return new ResponseEntity<>(driverService.getDriverRatingById(driverId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DriverResponseDTO> editDriverProfile(Long driverId, DriverRequestDTO driverRequestDTO) {
        return new ResponseEntity<>(driverService.editDriverProfile(driverId, driverRequestDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DriverResponseDTO> deleteDriverProfile(Long driverId) {
        return new ResponseEntity<>(driverService.deleteDriverProfile(driverId), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Page<DriverResponseDTO>> getAllDrivers(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(driverService.getAllDrivers(offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DriverResponseDTO> addDriver(DriverRequestDTO driverRequestDTO){
        return new ResponseEntity<>(driverService.addDriver(driverRequestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<DriverVehicleResponseDTO> getDriverWithVehicle(Long driverId) {
        return new ResponseEntity<>(driverService.getDriverWithVehicleById(driverId), HttpStatus.OK);
    }
}
