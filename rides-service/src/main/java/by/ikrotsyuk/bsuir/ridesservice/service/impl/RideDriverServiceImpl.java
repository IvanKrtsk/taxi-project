package by.ikrotsyuk.bsuir.ridesservice.service.impl;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.mapper.RideMapper;
import by.ikrotsyuk.bsuir.ridesservice.repository.RideRepository;
import by.ikrotsyuk.bsuir.ridesservice.service.RideDriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RideDriverServiceImpl implements RideDriverService {
    private final RideMapper rideMapper;
    private final RideRepository rideRepository;

    @Override
    public Page<RideResponseDTO> getAvailableRides(Long driverId) {
        return null;
    }

    @Override
    public RideFullResponseDTO acceptRide(Long driverId, Long rideId) {
        return null;
    }

    @Override
    public RideFullResponseDTO refuseRide(Long driverId, Long rideId) {
        return null;
    }

    @Override
    public RideFullResponseDTO beginRide(Long driverId, Long rideId) {
        return null;
    }

    @Override
    public RideFullResponseDTO endRide(Long driverId, Long rideId) {
        return null;
    }

    @Override
    public Page<RideFullResponseDTO> getRidesHistory(Long driverId) {
        return null;
    }

    @Override
    public RideFullResponseDTO getCurrentRide(Long driverId) {
        return null;
    }
}
