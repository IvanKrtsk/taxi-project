package by.ikrotsyuk.bsuir.driverservice.service;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;

import java.util.List;

public interface VehicleService {
    VehicleResponseDTO addVehicle(Long driverId, VehicleRequestDTO vehicleRequestDTO);
    VehicleResponseDTO editVehicle(Long driverId, VehicleRequestDTO vehicleRequestDTO);
    Boolean makeVehicleCurrent(Long driverId, Long vehicleId);
    Boolean makeVehicleUncurrent(Long driverId, Long vehicleId);
    List<VehicleResponseDTO> getAllVehiclesByType(CarClassTypes type);
    List<VehicleResponseDTO> getAllVehiclesByYear(Integer year);
    List<VehicleResponseDTO> getAllVehiclesByBrand(String brand);
}
