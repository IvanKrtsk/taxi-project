package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.VehicleOperations;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.driverservice.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/driver")
public class VehicleController implements VehicleOperations {
    private final VehicleService vehicleService;

    @Override
    public ResponseEntity<VehicleResponseDTO> addVehicle(@PathVariable Long driverId,@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<VehicleResponseDTO> editVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId, @Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<VehicleResponseDTO> chooseCurrentVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId) {
        return null;
    }

    @Override
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehicles() {
        return null;
    }

    @Override
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long vehicleId) {
        return null;
    }

    @Override
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByType(@RequestParam CarClassTypes type) {
        return null;
    }

    @Override
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByYear(@RequestParam Integer year) {
        return null;
    }

    @Override
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByBrand(@RequestParam String brand) {
        return null;
    }
}
