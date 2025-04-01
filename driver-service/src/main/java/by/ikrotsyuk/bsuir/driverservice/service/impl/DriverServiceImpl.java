package by.ikrotsyuk.bsuir.driverservice.service.impl;

import by.ikrotsyuk.bsuir.driverservice.dto.DriverRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.mapper.DriverMapper;
import by.ikrotsyuk.bsuir.driverservice.repository.DriverRepository;
import by.ikrotsyuk.bsuir.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Override
    @Transactional(readOnly = true)
    public DriverResponseDTO getDriverProfileById(Long id){
        DriverEntity driverEntity = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        return driverMapper.toDTO(driverEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getDriverRatingById(Long id){
        return getDriverProfileById(id).getRating();
    }

    @Override
    @Transactional
    public DriverResponseDTO editDriverProfile(Long id, DriverRequestDTO driverRequestDTO){
        DriverEntity driverEntity = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        String email = driverRequestDTO.getEmail();
        String phone = driverRequestDTO.getPhone();
        if(!driverEntity.getEmail().equals(email)){
            // check email unique
            driverEntity.setEmail(email);
            // activates keycloak email change
        }
        if(!driverEntity.getPhone().equals(phone)){
            // check email unique
            driverEntity.setPhone(phone);
            // activates keycloak phone change
        }
        driverEntity.setName(driverRequestDTO.getName());
        return driverMapper.toDTO(driverEntity);
    }

    @Override
    @Transactional
    public DriverResponseDTO deleteDriverProfile(Long id){
        DriverEntity driverEntity = driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
        if(driverEntity.getIsDeleted())
            throw new RuntimeException("not found");
        driverEntity.setIsDeleted(true);
        return driverMapper.toDTO(driverEntity);
    }

    @Override
    @Transactional
    public Long checkIsEmailCorrect(Long id, String email){
        return driverRepository.findById(id)
                .filter(driverEntity -> driverEntity.getEmail().equals(email))
                .map(DriverEntity::getId)
                .or(() -> driverRepository.findByEmail(email)
                        .map(DriverEntity::getId))
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Override
    @Transactional
    public Boolean addDriver(String email){
        driverRepository.save(DriverEntity.builder()
                        .name("not specified")
                        .email(email)
                        .phone("not specified")
                        .rating(0.0)
                        .total_rides(0L)
                        .isDeleted(false)
                .build());
        return true;
    }
}
