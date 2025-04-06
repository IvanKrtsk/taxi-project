package by.ikrotsyuk.bsuir.ridesservice.service;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface RidePassengerService {
    BigDecimal getCostOfRide(Long passengerId, RideRequestDTO rideRequestDTO);
    Page<RideFullResponseDTO> getRidesStory(Long passengerId, int offset, int itemCount, String field, Boolean isSortDirectionAsc);
    RideResponseDTO bookRide(Long passengerId, RideRequestDTO rideRequestDTO);
    RideFullResponseDTO getRideInfo(Long passengerId, Long rideId);
    RideFullResponseDTO refuseRide(Long passengerId, Long rideId);
}
