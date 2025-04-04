package by.ikrotsyuk.bsuir.ridesservice.service;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface RidePassengerService {
    BigDecimal getCostOfRide(Long passengerId, String startLocation, String endLocation);
    Page<RideFullResponseDTO> getRidesStory(Long passengerId);
    RideResponseDTO bookRide(Long passengerId, RideRequestDTO rideRequestDTO);
    RideFullResponseDTO getCurrentRideInfo(Long passengerId, Long rideId);
    RideFullResponseDTO getRideInfo(Long passengerId, Long rideId);
}
