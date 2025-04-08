package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.controller.VehicleOperations;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypesDriver;
import by.ikrotsyuk.bsuir.driverservice.service.VehicleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
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
public class VehicleControllerImpl implements VehicleOperations {
    private final VehicleService vehicleService;

    /**
     *
     * @param driverId - в дальнейшем будет браться из jwt
     */
    @Override
    @PostMapping("/{driverId}")
    public ResponseEntity<VehicleResponseDTO> addVehicle(@PathVariable Long driverId, @Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
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
    @GetMapping("")
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehicles(@RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(vehicleService.getAllVehicles(offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long vehicleId) {
        return new ResponseEntity<>(vehicleService.getVehicleById(vehicleId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/class")
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByType(@RequestParam CarClassTypesDriver type, @RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(vehicleService.getAllVehiclesByType(type, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @GetMapping("/year")
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByYear(@RequestParam Integer year, @RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc) {
        return new ResponseEntity<>(vehicleService.getAllVehiclesByYear(year, offset, itemCount, field, isSortDirectionAsc), HttpStatus.OK);
    }

    @Override
    @GetMapping("/brand")
    public ResponseEntity<Page<VehicleResponseDTO>> getAllVehiclesByBrand(@RequestParam String brand, @RequestParam int offset, @RequestParam int itemCount, @RequestParam(required = false) String field, @RequestParam(required = false) Boolean isSortDirectionAsc) {
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
    @DeleteMapping("/vehicle/{driverId}/{vehicleId}")
    public ResponseEntity<VehicleResponseDTO> deleteVehicle(@PathVariable Long driverId, @PathVariable Long vehicleId) {
        return new ResponseEntity<>(vehicleService.deleteVehicleById(driverId, vehicleId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/{driverId}")
    public ResponseEntity<List<VehicleResponseDTO>> getAllDriverVehicles(@PathVariable Long driverId) {
        return new ResponseEntity<>(vehicleService.getAllDriverVehicles(driverId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/current/{driverId}")
    public ResponseEntity<VehicleResponseDTO> getDriverCurrentVehicle(@PathVariable Long driverId) {
        return new ResponseEntity<>(vehicleService.getDriverCurrentVehicle(driverId), HttpStatus.OK);
    }
}
