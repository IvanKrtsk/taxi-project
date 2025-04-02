package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.VehicleOperations;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.driverservice.service.VehicleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/driver")
public class VehicleController implements VehicleOperations {
    private final VehicleService vehicleService;

    /**
     *
     * @param driverId - в дальнейшем будет браться из jwt
     */
    @Override
    @PostMapping("/vehicles/{driverId}")
    public ResponseEntity<VehicleResponseDTO> addVehicle(@PathVariable Long driverId, @Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        return new ResponseEntity<>(vehicleService.addVehicle(driverId, vehicleRequestDTO), HttpStatus.CREATED);
    }

    /**
     *
     * @param driverId - в дальнейшем будет браться из jwt
     */
    @Override
    @PatchMapping("/vehicles/edit/{driverId}/{vehicleId}")
    public ResponseEntity<VehicleResponseDTO> editVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId, @Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        return new ResponseEntity<>(vehicleService.editVehicle(driverId, vehicleId, vehicleRequestDTO), HttpStatus.OK);
    }

    /**
     *
     * @param driverId - в дальнейшем будет браться из jwt
     */
    @Override
    @PatchMapping("/vehicles/choose/{driverId}/{vehicleId}")
    public ResponseEntity<VehicleResponseDTO> chooseCurrentVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId) {
        return new ResponseEntity<>(vehicleService.chooseCurrentVehicle(driverId, vehicleId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/vehicles")
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehicles(@RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc) {
        return new ResponseEntity<>(vehicleService.getAllVehicles(offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @GetMapping("/vehicles/{vehicleId}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long vehicleId) {
        return new ResponseEntity<>(vehicleService.getVehicleById(vehicleId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/vehicles/class")
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByType(@RequestParam CarClassTypes type, @RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc) {
        return new ResponseEntity<>(vehicleService.getAllVehiclesByType(type, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @GetMapping("/vehicles/year")
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByYear(@RequestParam Integer year, @RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc) {
        return new ResponseEntity<>(vehicleService.getAllVehiclesByYear(year, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @GetMapping("/vehicles/brand")
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByBrand(@RequestParam String brand, @RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc) {
        return new ResponseEntity<>(vehicleService.getAllVehiclesByBrand(brand, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @GetMapping("/vehicle/license")
    public ResponseEntity<VehicleResponseDTO> getVehicleByLicensePlate(@Size(max = 20) @RequestParam String licensePlate) {
        return new ResponseEntity<>(vehicleService.getVehicleByLicense(licensePlate), HttpStatus.OK);
    }

    /**
     *
     * @param driverId - в дальнейшем будет браться из jwt
     */
    @Override
    @GetMapping("/vehicle/{driverId}/{vehicleId}")
    public ResponseEntity<VehicleResponseDTO> deleteVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId) {
        return new ResponseEntity<>(vehicleService.deleteVehicleById(driverId, vehicleId), HttpStatus.OK);
    }
}
