package by.ikrotsyuk.bsuir.passengerservice.service;

import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;

public interface PassengerService {
    PassengerResponseDTO getPassengerById(Long id);
    Double getPassengerRatingById(Long id);
    PassengerResponseDTO editPassengerProfile(Long id, PassengerRequestDTO passengerRequestDTO);
    PassengerResponseDTO deletePassengerProfile(Long id);
    Long checkIsEmailCorrect(Long id, String email);
    Boolean addPassenger(String email);
}
