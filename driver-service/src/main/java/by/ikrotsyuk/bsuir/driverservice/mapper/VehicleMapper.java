package by.ikrotsyuk.bsuir.driverservice.mapper;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    @Mapping(target = "driverId", source = "driver.id")
    VehicleResponseDTO toDTO(VehicleEntity vehicleEntity);

    List<VehicleResponseDTO> toDTOList(List<VehicleEntity> vehicleEntityList);

    VehicleEntity toEntity(VehicleRequestDTO vehicleRequestDTO);

    default VehicleEntity toEntityWithDefault(VehicleRequestDTO vehicleRequestDTO){
        VehicleEntity vehicleEntity = toEntity(vehicleRequestDTO);
        vehicleEntity.setRidesCount(0L);
        vehicleEntity.setIsCurrent(false);
        return vehicleEntity;
    }
}
