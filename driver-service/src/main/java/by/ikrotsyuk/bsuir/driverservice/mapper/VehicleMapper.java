package by.ikrotsyuk.bsuir.driverservice.mapper;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleResponseDTO toDTO(VehicleEntity vehicleEntity);

    Page<VehicleResponseDTO> toDTOPage(Page<VehicleEntity> vehicleEntityPage);

    List<VehicleResponseDTO> toDTOList(List<VehicleEntity> vehicleEntityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ridesCount", ignore = true)
    @Mapping(target = "driver", ignore = true)
    @Mapping(target = "isCurrent", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    VehicleEntity toEntity(VehicleRequestDTO vehicleRequestDTO);
}
