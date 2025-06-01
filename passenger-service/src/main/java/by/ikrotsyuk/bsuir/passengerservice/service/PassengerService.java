package by.ikrotsyuk.bsuir.passengerservice.service;

import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.passengerservice.entity.customtypes.PaymentTypeTypes;
import org.springframework.data.domain.Page;

public interface PassengerService {
    PassengerResponseDTO getPassengerById(Long id);

    Double getPassengerRatingById(Long id);

    PassengerResponseDTO editPassengerProfile(Long id, PassengerRequestDTO passengerRequestDTO);

    PassengerResponseDTO deletePassengerProfile(Long id);

    PassengerResponseDTO addPassenger(PassengerRequestDTO passengerRequestDTO);

    Page<PassengerResponseDTO> getAllPassengers(int offset, int itemCount, String field, Boolean isSortDirectionAsc);

    PassengerResponseDTO changePaymentType(Long id, PaymentTypeTypes paymentType);
}
