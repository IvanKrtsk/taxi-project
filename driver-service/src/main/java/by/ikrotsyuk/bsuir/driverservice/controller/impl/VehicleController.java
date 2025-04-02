package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.VehicleOperations;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.driverservice.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("/all")
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehicles() {
        return new ResponseEntity<>(vehicleService.getAllVehicles(), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long vehicleId) {
        return new ResponseEntity<>(vehicleService.getVehicleById(vehicleId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/class")
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByType(@RequestParam CarClassTypes type) {
        return new ResponseEntity<>(vehicleService.getAllVehiclesByType(type), HttpStatus.OK);
    }

    @Override
    @GetMapping("/year")
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByYear(@RequestParam Integer year) {
        return new ResponseEntity<>(vehicleService.getAllVehiclesByYear(year), HttpStatus.OK);
    }

    @Override
    @GetMapping("/brand")
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByBrand(@RequestParam String brand) {
        return new ResponseEntity<>(vehicleService.getAllVehiclesByBrand(brand), HttpStatus.OK);
    }
}
