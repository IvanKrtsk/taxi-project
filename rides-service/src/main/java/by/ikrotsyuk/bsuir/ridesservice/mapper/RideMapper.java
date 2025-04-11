package by.ikrotsyuk.bsuir.ridesservice.mapper;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideRequestDTO;
import by.ikrotsyuk.bsuir.ridesservice.dto.RideResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.entity.RideEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RideMapper {
    RideFullResponseDTO toFullDTO(RideEntity rideEntity);

    RideEntity toEntity(RideRequestDTO rideRequestDTO);

    RideResponseDTO toDTO(RideEntity rideEntity);
}
