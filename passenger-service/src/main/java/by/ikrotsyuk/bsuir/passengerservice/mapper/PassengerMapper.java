package by.ikrotsyuk.bsuir.passengerservice.mapper;

import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.passengerservice.entity.PassengerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    PassengerResponseDTO toDTO(PassengerEntity passengerEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "totalRides", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    PassengerEntity toEntityWithoutIdDeleted(PassengerRequestDTO passengerRequestDTO);

    default PassengerEntity toEntity(PassengerRequestDTO passengerRequestDTO, boolean isDeleted){
        PassengerEntity passengerEntity = toEntityWithoutIdDeleted(passengerRequestDTO);
        passengerEntity.setIsDeleted(isDeleted);
        return passengerEntity;
    }
}
