package by.ikrotsyuk.bsuir.driverservice.controller;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface VehicleOperations {
    @PostMapping("/{driverId}")
    ResponseEntity<VehicleResponseDTO> addVehicle(@PathVariable Long driverId, @Valid @RequestBody VehicleRequestDTO vehicleRequestDTO);

    @PatchMapping("/{driverId}/edit/{vehicleId}")
    ResponseEntity<VehicleResponseDTO> editVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId, @Valid @RequestBody VehicleRequestDTO vehicleRequestDTO);

    @PatchMapping("/{driverId}/choose/{vehicleId}")
    ResponseEntity<VehicleResponseDTO> chooseCurrentVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId);

    @GetMapping
    ResponseEntity<Page<VehicleResponseDTO>> getAllVehicles(@RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc);

    @GetMapping("/{vehicleId}")
    ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long vehicleId);

    @DeleteMapping("/{driverId}/{vehicleId}")
    ResponseEntity<VehicleResponseDTO> deleteVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId);

    @GetMapping("/{driverId}/all")
    ResponseEntity<List<VehicleResponseDTO>> getAllDriverVehicles(@PathVariable Long driverId);

    @GetMapping("/{driverId}/current")
    ResponseEntity<VehicleResponseDTO> getDriverCurrentVehicle(@PathVariable Long driverId);
}
