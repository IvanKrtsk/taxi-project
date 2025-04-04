package by.ikrotsyuk.bsuir.ridesservice.service.impl;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.service.RideAdminService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class RideAdminServiceImpl implements RideAdminService {
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
