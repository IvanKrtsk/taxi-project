package by.ikrotsyuk.bsuir.passengerservice.mapper;

import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.passengerservice.entity.PassengerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    PassengerResponseDTO toDTO(PassengerEntity passengerEntity);

    PassengerEntity toEntityWithoutIsDeleted(PassengerRequestDTO passengerRequestDTO);

    default PassengerEntity toEntity(PassengerRequestDTO passengerRequestDTO, boolean isDeleted){
        PassengerEntity passengerEntity = toEntityWithoutIsDeleted(passengerRequestDTO);
        passengerEntity.setIsDeleted(isDeleted);
        return passengerEntity;
    }
}
