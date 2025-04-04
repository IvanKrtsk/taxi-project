package by.ikrotsyuk.bsuir.ridesservice.service;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import org.springframework.data.domain.Page;

public interface RideAdminService {
    RideFullResponseDTO editRide(Long rideId);
    RideFullResponseDTO deleteRide(Long rideId);
    Page<RideFullResponseDTO> getAllRides();
    RideFullResponseDTO getRideById(Long rideId);
}
