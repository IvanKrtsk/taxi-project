package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.VehicleOperations;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.driverservice.service.impl.VehicleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/driver")
public class VehicleController implements VehicleOperations {
    private final VehicleServiceImpl vehicleService;

    @Override
    public ResponseEntity<VehicleResponseDTO> addVehicle(Long driverId, VehicleRequestDTO vehicleRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<VehicleResponseDTO> editVehicle(Long driverId, VehicleRequestDTO vehicleRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> makeVehicleCurrent(Long driverId, Long vehicleId) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> makeVehicleUncurrent(Long driverId, Long vehicleId) {
        return null;
    }

    @Override
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByType(CarClassTypes type) {
        return null;
    }

    @Override
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByYear(Integer year) {
        return null;
    }

    @Override
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByBrand(String brand) {
        return null;
    }
}
