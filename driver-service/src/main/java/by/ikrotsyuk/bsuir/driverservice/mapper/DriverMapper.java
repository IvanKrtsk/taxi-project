package by.ikrotsyuk.bsuir.driverservice.mapper;

import by.ikrotsyuk.bsuir.driverservice.dto.DriverRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverVehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverResponseDTO toDTO(DriverEntity driverEntity);

    DriverVehicleResponseDTO toDVDTO(DriverEntity driverEntity);

    List<DriverResponseDTO> toDTOList(List<DriverEntity> driverEntityList);

    DriverEntity toEntityWithoutIsDeleted(DriverRequestDTO driverRequestDTO);

    default DriverEntity toEntity(DriverRequestDTO driverRequestDTO, boolean isDeleted){
        DriverEntity driverEntity = toEntityWithoutIsDeleted(driverRequestDTO);
        driverEntity.setIsDeleted(isDeleted);
        return driverEntity;
    }
}
