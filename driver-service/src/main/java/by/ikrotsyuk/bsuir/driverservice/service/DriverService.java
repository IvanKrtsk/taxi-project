package by.ikrotsyuk.bsuir.driverservice.service;

import by.ikrotsyuk.bsuir.driverservice.dto.*;
import org.springframework.data.domain.Page;

public interface DriverService {
    DriverResponseDTO getDriverProfileById(Long driverId);
    Double getDriverRatingById(Long driverId);
    DriverResponseDTO editDriverProfile(Long driverId, DriverRequestDTO driverRequestDTO);
    DriverResponseDTO deleteDriverProfile(Long driverId);
    DriverResponseDTO addDriver(DriverRequestDTO driverRequestDTO);
    Page<DriverResponseDTO> getAllDrivers(int offset, int itemCount, String field, Boolean isSortDirectionAsc);
    DriverVehicleResponseDTO getDriverWithVehicleById(Long driverId);
}
