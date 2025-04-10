package by.ikrotsyuk.bsuir.ridesservice.service;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import org.springframework.data.domain.Page;

public interface RideDriverService {
    Page<RideResponseDTO> getAvailableRides(Long driverId, int offset, int itemCount, String field, Boolean isSortDirectionAsc);
    RideFullResponseDTO acceptRide(Long driverId, Long rideId);
    RideFullResponseDTO refuseRide(Long driverId, Long rideId);
    RideFullResponseDTO beginRide(Long driverId, Long rideId);
    RideFullResponseDTO endRide(Long driverId, Long rideId);
    Page<RideFullResponseDTO> getRidesHistory(Long driverId, int offset, int itemCount, String field, Boolean isSortDirectionAsc);
    RideFullResponseDTO getCurrentRide(Long driverId);
}
