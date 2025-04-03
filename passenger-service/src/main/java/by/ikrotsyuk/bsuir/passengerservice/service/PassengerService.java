package by.ikrotsyuk.bsuir.passengerservice.service;

import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import org.springframework.data.domain.Page;

public interface PassengerService {
    PassengerResponseDTO getPassengerById(Long id);
    Double getPassengerRatingById(Long id);
    PassengerResponseDTO editPassengerProfile(Long id, PassengerRequestDTO passengerRequestDTO);
    PassengerResponseDTO deletePassengerProfile(Long id);
    Long checkIsEmailCorrect(Long id, String email);
    PassengerResponseDTO addPassenger(String email);
    Page<PassengerResponseDTO> getAllPassengers(String brand, int offset, int itemCount, String field, Boolean isSortDirectionAsc);
}
