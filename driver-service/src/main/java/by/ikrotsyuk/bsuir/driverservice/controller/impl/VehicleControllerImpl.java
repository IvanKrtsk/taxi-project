package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.VehicleOperations;
import by.ikrotsyuk.bsuir.driverservice.controller.VehiclesByCriterionOperation;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.driverservice.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/drivers/vehicles")
public class VehicleControllerImpl implements VehicleOperations, VehiclesByCriterionOperation {
    private final VehicleService vehicleService;

    @Override
    public ResponseEntity<VehicleResponseDTO> addVehicle(Long driverId, VehicleRequestDTO vehicleRequestDTO) {
        return new ResponseEntity<>(vehicleService.addVehicle(driverId, vehicleRequestDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<VehicleResponseDTO> editVehicle(Long driverId, Long vehicleId, VehicleRequestDTO vehicleRequestDTO) {
        return new ResponseEntity<>(vehicleService.editVehicle(driverId, vehicleId, vehicleRequestDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VehicleResponseDTO> chooseCurrentVehicle(Long driverId, Long vehicleId) {
        return new ResponseEntity<>(vehicleService.chooseCurrentVehicle(driverId, vehicleId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehicles(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(vehicleService.getAllVehicles(offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VehicleResponseDTO> getVehicleById(Long vehicleId) {
        return new ResponseEntity<>(vehicleService.getVehicleById(vehicleId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByType(CarClassTypes type, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(vehicleService.getAllVehiclesByType(type, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByYear(Integer year, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(vehicleService.getAllVehiclesByYear(year, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByBrand(String brand, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(vehicleService.getAllVehiclesByBrand(brand, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VehicleResponseDTO> getVehicleByLicensePlate(String licensePlate) {
        return new ResponseEntity<>(vehicleService.getVehicleByLicense(licensePlate), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VehicleResponseDTO> deleteVehicle(Long driverId, Long vehicleId) {
        return new ResponseEntity<>(vehicleService.deleteVehicleById(driverId, vehicleId), HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<VehicleResponseDTO>> getAllDriverVehicles(Long driverId) {
        return new ResponseEntity<>(vehicleService.getAllDriverVehicles(driverId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VehicleResponseDTO> getDriverCurrentVehicle(Long driverId) {
        return new ResponseEntity<>(vehicleService.getDriverCurrentVehicle(driverId), HttpStatus.OK);
    }
}
