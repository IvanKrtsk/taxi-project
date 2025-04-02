package by.ikrotsyuk.bsuir.driverservice.service.impl;

import by.ikrotsyuk.bsuir.driverservice.dto.DriverRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.*;
import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.mapper.DriverMapper;
import by.ikrotsyuk.bsuir.driverservice.mapper.VehicleMapper;
import by.ikrotsyuk.bsuir.driverservice.repository.DriverRepository;
import by.ikrotsyuk.bsuir.driverservice.service.DriverService;
import by.ikrotsyuk.bsuir.driverservice.service.validation.DriverServiceValidationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final VehicleMapper vehicleMapper;
    private final DriverServiceValidationManager driverServiceValidationManager;

    @Override
    @Transactional(readOnly = true)
    public DriverResponseDTO getDriverProfileById(Long driverId){
        DriverEntity driverEntity = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundByIdException(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_EMAIL_MESSAGE_KEY, driverId));
        return driverMapper.toDTO(driverEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getDriverRatingById(Long driverId){
        return getDriverProfileById(driverId).getRating();
    }

    @Override
    @Transactional
    public DriverResponseDTO editDriverProfile(Long driverId, DriverRequestDTO driverRequestDTO){
        DriverEntity driverEntity = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundByIdException(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_EMAIL_MESSAGE_KEY, driverId));
        String email = driverRequestDTO.getEmail();
        String phone = driverRequestDTO.getPhone();
        if(!driverEntity.getEmail().equals(email)){
            driverServiceValidationManager.checkEmailIsUnique(email);
            driverEntity.setEmail(email);
            // activates keycloak email change
        }
        if(!driverEntity.getPhone().equals(phone)){
            driverServiceValidationManager.checkPhoneIsUnique(phone);
            driverEntity.setPhone(phone);
            // activates keycloak phone change
        }
        driverEntity.setName(driverRequestDTO.getName());
        return driverMapper.toDTO(driverEntity);
    }

    @Override
    @Transactional
    public DriverResponseDTO deleteDriverProfile(Long driverId){
        DriverEntity driverEntity = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundByIdException(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_EMAIL_MESSAGE_KEY, driverId));
        if(driverEntity.getIsDeleted())
            throw new DriverAlreadyDeletedException(DriverExceptionMessageKeys.DRIVER_ALREADY_DELETED_MESSAGE_KEY, driverId);
        driverEntity.setIsDeleted(true);
        return driverMapper.toDTO(driverEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Long checkIsEmailCorrect(Long driverId, String email){
        return driverRepository.findById(driverId)
                .filter(driverEntity -> driverEntity.getEmail().equals(email))
                .map(DriverEntity::getId)
                .or(() -> driverRepository.findByEmail(email)
                        .map(DriverEntity::getId))
                .orElseThrow(() -> new DriverNotFoundByEmailException(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_EMAIL_MESSAGE_KEY, email));
    }

    @Override
    @Transactional
    public Boolean addDriver(String email){
        String NOT_SPECIFIED = "not specified";
        driverRepository.save(DriverEntity.builder()
                        .name(NOT_SPECIFIED)
                        .email(email)
                        .phone(NOT_SPECIFIED)
                        .rating(0.0)
                        .total_rides(0L)
                        .isDeleted(false)
                .build());
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DriverResponseDTO> getAllDrivers(int offset, int itemCount, String field, boolean isSortDirectionAsc) {
        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Page<DriverEntity> driverEntityPage = driverRepository.findAll(
                PageRequest.of(offset, itemCount,
                        Sort.by(sortDirection, field))
        );
        if(driverEntityPage.isEmpty())
            throw new DriversNotFoundException(DriverExceptionMessageKeys.DRIVERS_NOT_FOUND_MESSAGE_KEY);
        else
            return driverMapper.toDTOPage(driverEntityPage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleResponseDTO> getAllDriverVehicles(Long driverId) {
        DriverEntity driverEntity = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundByIdException(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_EMAIL_MESSAGE_KEY, driverId));
        List<VehicleEntity> vehicleResponseDTOList = driverEntity.getDriverVehicles();
        if(vehicleResponseDTOList.isEmpty())
            throw new DriverVehiclesNotFoundException(DriverExceptionMessageKeys.DRIVER_VEHICLES_NOT_FOUND_MESSAGE_KEY, driverId);
        return vehicleMapper.toDTOList(vehicleResponseDTOList);
    }

    @Override
    @Transactional(readOnly = true)
    public VehicleResponseDTO getDriverCurrentVehicle(Long driverId) {
        return getAllDriverVehicles(driverId).stream()
                .filter(VehicleResponseDTO::getIsCurrent)
                .findFirst()
                .orElseThrow(() -> new DriverCurrentVehicleNotFoundException(DriverExceptionMessageKeys.DRIVER_CURRENT_VEHICLE_NOT_FOUND, driverId));
    }
}
