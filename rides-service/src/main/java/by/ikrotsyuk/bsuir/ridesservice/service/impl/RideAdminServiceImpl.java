package by.ikrotsyuk.bsuir.ridesservice.service.impl;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.mapper.RideMapper;
import by.ikrotsyuk.bsuir.ridesservice.repository.RideRepository;
import by.ikrotsyuk.bsuir.ridesservice.service.RideAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RideAdminServiceImpl implements RideAdminService {
    private final RideMapper rideMapper;
    private final RideRepository rideRepository;

    @Override
    public RideFullResponseDTO editRide(Long rideId) {
        return null;
    }

    @Override
    public RideFullResponseDTO deleteRide(Long rideId) {
        return null;
    }

    @Override
    public Page<RideFullResponseDTO> getAllRides() {
        return null;
    }

    @Override
    public RideFullResponseDTO getRideById(Long rideId) {
        return null;
    }
}
