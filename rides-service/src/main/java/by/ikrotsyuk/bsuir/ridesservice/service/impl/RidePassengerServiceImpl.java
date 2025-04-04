package by.ikrotsyuk.bsuir.ridesservice.service.impl;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.mapper.RideMapper;
import by.ikrotsyuk.bsuir.ridesservice.repository.RideRepository;
import by.ikrotsyuk.bsuir.ridesservice.service.RidePassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class RidePassengerServiceImpl implements RidePassengerService {
    private final RideMapper rideMapper;
    private final RideRepository rideRepository;

    @Override
    public BigDecimal getCostOfRide(Long passengerId, RideRequestDTO rideRequestDTO) {
        return null;
    }

    @Override
    public Page<RideFullResponseDTO> getRidesStory(Long passengerId, int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
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
