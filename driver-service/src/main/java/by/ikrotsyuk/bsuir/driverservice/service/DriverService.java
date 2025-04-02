package by.ikrotsyuk.bsuir.driverservice.service;

import by.ikrotsyuk.bsuir.driverservice.dto.DriverRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface DriverService {
    DriverResponseDTO getDriverProfileById(Long driverId);
    Double getDriverRatingById(Long driverId);
    DriverResponseDTO editDriverProfile(Long driverId, DriverRequestDTO driverRequestDTO);
    DriverResponseDTO deleteDriverProfile(Long driverId);
    Long checkIsEmailCorrect(Long driverId, String email);
    Boolean addDriver(String email);
    Page<DriverResponseDTO> getAllDrivers(int offset, int itemCount, String field, boolean isSortDirectionAsc);
    List<VehicleResponseDTO> getAllDriverVehicles(Long driverId);
    VehicleResponseDTO getDriverCurrentVehicle(Long driverId);
}
