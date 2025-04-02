package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.VehicleOperations;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.driverservice.service.VehicleService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/driver")
public class VehicleController implements VehicleOperations {
    private final VehicleService vehicleService;

    /**
     *
     * @param driverId - в дальнейшем будет браться из jwt
     */
    @Override
    @PostMapping("/{driverId}")
    public ResponseEntity<VehicleResponseDTO> addVehicle(@PathVariable Long driverId,@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        return new ResponseEntity<>(vehicleService.addVehicle(driverId, vehicleRequestDTO), HttpStatus.CREATED);
    }

    /**
     *
     * @param driverId - в дальнейшем будет браться из jwt
     */
    @Override
    @PatchMapping("/edit/{driverId}/{vehicleId}")
    public ResponseEntity<VehicleResponseDTO> editVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId, @Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        return new ResponseEntity<>(vehicleService.editVehicle(driverId, vehicleId, vehicleRequestDTO), HttpStatus.OK);
    }

    /**
     *
     * @param driverId - в дальнейшем будет браться из jwt
     */
    @Override
    @PatchMapping("/choose/{driverId}/{vehicleId}")
    public ResponseEntity<VehicleResponseDTO> chooseCurrentVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId) {
        return new ResponseEntity<>(vehicleService.chooseCurrentVehicle(driverId, vehicleId), HttpStatus.OK);
    }

    @Override
    @GetMapping()
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehicles(@RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc) {
        return new ResponseEntity<>(vehicleService.getAllVehicles(offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long vehicleId) {
        return new ResponseEntity<>(vehicleService.getVehicleById(vehicleId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/class")
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByType(@RequestParam CarClassTypes type, @RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc) {
        return new ResponseEntity<>(vehicleService.getAllVehiclesByType(type, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @GetMapping("/year")
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByYear(@RequestParam Integer year, @RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc) {
        return new ResponseEntity<>(vehicleService.getAllVehiclesByYear(year, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @GetMapping("/brand")
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByBrand(@RequestParam String brand, @RequestParam int offset, @RequestParam int itemCount, @RequestParam String field, @RequestParam boolean isSortDirectionAsc) {
        return new ResponseEntity<>(vehicleService.getAllVehiclesByBrand(brand, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }
}
