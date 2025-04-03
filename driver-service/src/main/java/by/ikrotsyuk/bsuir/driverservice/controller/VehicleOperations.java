package by.ikrotsyuk.bsuir.driverservice.controller;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface VehicleOperations {
    ResponseEntity<VehicleResponseDTO> addVehicle(@PathVariable Long driverId, @Valid @RequestBody VehicleRequestDTO vehicleRequestDTO);
    ResponseEntity<VehicleResponseDTO> editVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId, @Valid @RequestBody VehicleRequestDTO vehicleRequestDTO);
    ResponseEntity<VehicleResponseDTO> chooseCurrentVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId);
    ResponseEntity<Page<VehicleResponseDTO>> getAllVehicles(@RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc);
    ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long vehicleId);
    ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByType(@RequestParam CarClassTypes type, @RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc);
    ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByYear(@RequestParam Integer year, @RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc);
    ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByBrand(@RequestParam String brand, @RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc);
    ResponseEntity<VehicleResponseDTO> getVehicleByLicensePlate(@Size(max = 20) @RequestParam String licensePlate);
    ResponseEntity<VehicleResponseDTO> deleteVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId);
}
