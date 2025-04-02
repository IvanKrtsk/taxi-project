package by.ikrotsyuk.bsuir.driverservice.service;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;

import java.util.List;

public interface VehicleService {
    VehicleResponseDTO addVehicle(Long driverId, VehicleRequestDTO vehicleRequestDTO);
    VehicleResponseDTO editVehicle(Long driverId, Long vehicleId, VehicleRequestDTO vehicleRequestDTO);
    VehicleResponseDTO chooseCurrentVehicle(Long driverId, Long vehicleId);
    VehicleResponseDTO getVehicleById(Long vehicleId);
    List<VehicleResponseDTO> getAllVehicles();
    List<VehicleResponseDTO> getAllVehiclesByType(CarClassTypes type);
    List<VehicleResponseDTO> getAllVehiclesByYear(Integer year);
    List<VehicleResponseDTO> getAllVehiclesByBrand(String brand);
}
