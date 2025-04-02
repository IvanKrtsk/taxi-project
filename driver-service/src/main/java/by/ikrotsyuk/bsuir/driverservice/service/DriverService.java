package by.ikrotsyuk.bsuir.driverservice.service;

import by.ikrotsyuk.bsuir.driverservice.dto.DriverRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;

import java.util.List;

public interface DriverService {
    DriverResponseDTO getDriverProfileById(Long id);
    Double getDriverRatingById(Long id);
    DriverResponseDTO editDriverProfile(Long id, DriverRequestDTO driverRequestDTO);
    DriverResponseDTO deleteDriverProfile(Long id);
    Long checkIsEmailCorrect(Long id, String email);
    Boolean addDriver(String email);
    List<DriverResponseDTO> getAllDrivers();
    List<VehicleResponseDTO> getAllDriverVehicles(Long id);
    VehicleResponseDTO getDriverCurrentVehicle(Long id);
}
