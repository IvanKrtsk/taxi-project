package by.ikrotsyuk.bsuir.driverservice.service;

import by.ikrotsyuk.bsuir.driverservice.dto.DriverRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;

public interface DriverService {
    DriverResponseDTO getDriverProfileById(Long id);
    Double getDriverRatingById(Long id);
    DriverResponseDTO editDriverProfile(Long id, DriverRequestDTO driverRequestDTO);
    DriverResponseDTO deleteDriverProfile(Long id);
    Long checkIsEmailCorrect(Long id, String email);
    Boolean addDriver(String email);
}
