package by.ikrotsyuk.bsuir.ridesservice.service;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import org.springframework.data.domain.Page;

public interface RideDriverService {
    Page<RideResponseDTO> getAvailableRides(Long driverId);
    RideFullResponseDTO acceptRide(Long driverId, Long rideId);
    RideFullResponseDTO refuseRide(Long driverId, Long rideId);
    RideFullResponseDTO beginRide(Long driverId, Long rideId);
    RideFullResponseDTO endRide(Long driverId, Long rideId);
    Page<RideFullResponseDTO> getRidesHistory(Long driverId);
    RideFullResponseDTO getCurrentRide(Long driverId);
}
