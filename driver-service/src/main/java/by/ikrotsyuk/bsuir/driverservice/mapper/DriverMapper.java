package by.ikrotsyuk.bsuir.driverservice.mapper;

import by.ikrotsyuk.bsuir.driverservice.dto.DriverRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverResponseDTO toDTO(DriverEntity driverEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "total_rides", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "driverVehicles", ignore = true)
    DriverEntity toEntityWithoutIsDeleted(DriverRequestDTO driverRequestDTO);

    default DriverEntity toEntity(DriverRequestDTO driverRequestDTO, boolean isDeleted){
        DriverEntity driverEntity = toEntityWithoutIsDeleted(driverRequestDTO);
        driverEntity.setIsDeleted(isDeleted);
        return driverEntity;
    }
}
