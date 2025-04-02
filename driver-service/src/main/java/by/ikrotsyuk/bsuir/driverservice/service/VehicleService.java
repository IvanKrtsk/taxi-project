package by.ikrotsyuk.bsuir.driverservice.service;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.CarClassTypes;
import org.springframework.data.domain.Page;

public interface VehicleService {
    VehicleResponseDTO addVehicle(Long driverId, VehicleRequestDTO vehicleRequestDTO);
    VehicleResponseDTO editVehicle(Long driverId, Long vehicleId, VehicleRequestDTO vehicleRequestDTO);
    VehicleResponseDTO chooseCurrentVehicle(Long driverId, Long vehicleId);
    VehicleResponseDTO getVehicleById(Long vehicleId);
    Page<VehicleResponseDTO> getAllVehicles(int offset, int itemCount, String field, boolean isSortDirectionAsc);
    Page<VehicleResponseDTO> getAllVehiclesByType(CarClassTypes type, int offset, int itemCount, String field, boolean isSortDirectionAsc);
    Page<VehicleResponseDTO> getAllVehiclesByYear(Integer year, int offset, int itemCount, String field, boolean isSortDirectionAsc);
    Page<VehicleResponseDTO> getAllVehiclesByBrand(String brand, int offset, int itemCount, String field, boolean isSortDirectionAsc);
    VehicleResponseDTO getVehicleByLicense(String licensePlate);
    VehicleResponseDTO deleteVehicleById(Long driverId, Long vehicleId);
}
