package by.ikrotsyuk.bsuir.ridesservice.service.impl;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.service.RidePassengerService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RidePassengerServiceImpl implements RidePassengerService {
    @Override
    public BigDecimal getCostOfRide(Long passengerId, String startLocation, String endLocation) {
        return null;
    }

    @Override
    public Page<RideFullResponseDTO> getRidesStory(Long passengerId) {
        return null;
    }

    @Override
    public RideResponseDTO bookRide(Long passengerId, RideRequestDTO rideRequestDTO) {
        return null;
    }

    @Override
    public RideFullResponseDTO getCurrentRideInfo(Long passengerId, Long rideId) {
        return null;
    }

    @Override
    public RideFullResponseDTO getRideInfo(Long passengerId, Long rideId) {
        return null;
    }
}
