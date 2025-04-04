package by.ikrotsyuk.bsuir.ridesservice.mapper;

import by.ikrotsyuk.bsuir.ridesservice.dto.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ridesservice.entity.RideEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RideMapper {
    RideFullResponseDTO toFullDTO(RideEntity rideEntity);
}
