package by.ikrotsyuk.bsuir.ridesservice.service;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

public interface RideAdminService {
    RideFullResponseDTO editRide(Long rideId, RideFullRequestDTO rideFullRequestDTO);
    RideFullResponseDTO deleteRide(Long rideId);
    Page<RideFullResponseDTO> getAllRides(int offset, int itemCount, String field, Boolean isSortDirectionAsc);
    RideFullResponseDTO getRideById(Long rideId);
}
