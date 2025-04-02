package by.ikrotsyuk.bsuir.driverservice.controller;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface VehicleOperations {
    ResponseEntity<VehicleResponseDTO> addVehicle(@PathVariable Long driverId, @RequestBody VehicleRequestDTO vehicleRequestDTO);
    ResponseEntity<VehicleResponseDTO> editVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId, @RequestBody VehicleRequestDTO vehicleRequestDTO);
    ResponseEntity<VehicleResponseDTO> chooseCurrentVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId);
    ResponseEntity<Page<VehicleResponseDTO>> getAllVehicles(@RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc);
    ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long vehicleId);
    ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByType(@RequestParam CarClassTypes type, @RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc);
    ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByYear(@RequestParam Integer year, @RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc);
    ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByBrand(@RequestParam String brand, @RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc);
}
