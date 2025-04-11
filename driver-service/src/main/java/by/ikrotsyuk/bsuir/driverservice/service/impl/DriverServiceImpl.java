package by.ikrotsyuk.bsuir.driverservice.service.impl;

import by.ikrotsyuk.bsuir.driverservice.dto.*;
import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.customtypes.StatusTypes;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.*;
import by.ikrotsyuk.bsuir.driverservice.mapper.DriverMapper;
import by.ikrotsyuk.bsuir.driverservice.repository.DriverRepository;
import by.ikrotsyuk.bsuir.driverservice.service.DriverService;
import by.ikrotsyuk.bsuir.driverservice.service.utils.PaginationUtil;
import by.ikrotsyuk.bsuir.driverservice.service.validation.DriverServiceValidationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final DriverServiceValidationManager driverServiceValidationManager;
    private final PaginationUtil paginationUtil;

    @Override
    @Transactional(readOnly = true)
    public DriverResponseDTO getDriverProfileById(Long driverId){
        DriverEntity driverEntity = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundByIdException(driverId));
        return driverMapper.toDTO(driverEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getDriverRatingById(Long driverId){
        return getDriverProfileById(driverId).rating();
    }

    @Override
    @Transactional
    public DriverResponseDTO editDriverProfile(Long driverId, DriverRequestDTO driverRequestDTO){
        DriverEntity driverEntity = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundByIdException(driverId));
        String email = driverRequestDTO.email();
        String phone = driverRequestDTO.phone();
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
        driverEntity.setName(driverRequestDTO.name());
        return driverMapper.toDTO(driverEntity);
    }

    @Override
    @Transactional
    public DriverResponseDTO deleteDriverProfile(Long driverId){
        DriverEntity driverEntity = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundByIdException(driverId));
        if(driverEntity.getIsDeleted())
            throw new DriverAlreadyDeletedException(driverId);
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
                .orElseThrow(() -> new DriverNotFoundByEmailException(email));
    }

    @Override
    @Transactional
    public DriverResponseDTO addDriver(DriverRequestDTO driverRequestDTO){
        String email = driverRequestDTO.email();
        String phone = driverRequestDTO.phone();
        if(driverRepository.existsByEmail(email)) {
            DriverEntity driverEntity = driverRepository.findByEmail(email)
                    .orElseThrow(() -> new DriverNotFoundByEmailException(email));
            if(driverEntity.getIsDeleted()) {
                driverEntity.setIsDeleted(false);
                return driverMapper.toDTO(driverEntity);
            } else
                throw new DriverWithSameEmailAlreadyExistsException(email);
        } else {
            driverServiceValidationManager.checkEmailIsUnique(email);
            driverServiceValidationManager.checkPhoneIsUnique(phone);
            return driverMapper.toDTO(driverRepository.save(DriverEntity.builder()
                    .name(driverRequestDTO.name())
                    .email(email)
                    .phone(phone)
                    .total_rides(0L)              //  @Builder.Default null issue
                    .status(StatusTypes.AVAILABLE)
                    .build()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DriverResponseDTO> getAllDrivers(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        Page<DriverEntity> driverEntities = driverRepository.findAll(
                paginationUtil.getPageRequest(offset, itemCount, field, isSortDirectionAsc, DriverEntity.class));
        if(!driverEntities.hasContent())
            throw new DriversNotFoundException();
        else
            return driverEntities.map(driverMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public DriverVehicleResponseDTO getDriverWithVehicleById(Long driverId) {
        DriverEntity driverEntity = driverRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundByIdException(driverId));
        List<VehicleEntity> vehicleEntityList = driverEntity.getDriverVehicles();
        return driverMapper.toDVDTO(driverEntity);
    }
}
