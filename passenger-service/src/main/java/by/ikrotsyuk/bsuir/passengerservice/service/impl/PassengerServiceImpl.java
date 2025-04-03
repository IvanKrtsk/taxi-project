package by.ikrotsyuk.bsuir.passengerservice.service.impl;

import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.passengerservice.entity.PassengerEntity;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerAlreadyDeletedException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerNotFoundByEmailException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerNotFoundByIdException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengersNotFoundException;
import by.ikrotsyuk.bsuir.passengerservice.mapper.PassengerMapper;
import by.ikrotsyuk.bsuir.passengerservice.repository.PassengerRepository;
import by.ikrotsyuk.bsuir.passengerservice.service.PassengerService;
import by.ikrotsyuk.bsuir.passengerservice.service.validation.impl.PassengerServiceValidationManagerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final PassengerServiceValidationManagerImpl passengerServiceValidationManagerImpl;


    @Override
    @Transactional(readOnly = true)
    public PassengerResponseDTO getPassengerById(Long id){
       PassengerEntity passengerEntity = passengerRepository.findById(id)
               .orElseThrow(() -> new PassengerNotFoundByIdException(id));
        return passengerMapper.toDTO(passengerEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getPassengerRatingById(Long id){
        return this.getPassengerById(id).rating();
    }

    /**
     * активирует смену email в keycloak
     */
    @Override
    @Transactional
    public PassengerResponseDTO editPassengerProfile(Long id, PassengerRequestDTO passengerRequestDTO) {
        PassengerEntity passengerEntity = passengerRepository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundByIdException(id));
        String email = passengerRequestDTO.email();
        String phone = passengerRequestDTO.phone();
        if(!passengerEntity.getEmail().equals(email)){
            passengerServiceValidationManagerImpl.checkEmailIsUnique(email);
            passengerEntity.setEmail(email);
            // activates keycloak email change
        }
        if(!passengerEntity.getPhone().equals(phone)){
            passengerServiceValidationManagerImpl.checkPhoneIsUnique(phone);
            passengerEntity.setPhone(phone);
            // activates keycloak phone change
        }
        passengerEntity.setName(passengerRequestDTO.name());
        return passengerMapper.toDTO(passengerEntity);
    }

    @Override
    @Transactional
    public PassengerResponseDTO deletePassengerProfile(Long id) {
        PassengerEntity passengerEntity = passengerRepository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundByIdException(id));
        if(passengerEntity.getIsDeleted())
            throw new PassengerAlreadyDeletedException(id);
        passengerEntity.setIsDeleted(true);
        return passengerMapper.toDTO(passengerEntity);
    }

    /**
     * для auth service(проверяет соответствие email и id)
     */
    @Override
    @Transactional(readOnly = true)
    public Long checkIsEmailCorrect(Long id, String email) {
        return passengerRepository.findById(id)
                .filter(passengerEntity -> passengerEntity.getEmail().equals(email))
                .map(PassengerEntity::getId)
                .or(() -> passengerRepository.findByEmail(email)
                        .map(PassengerEntity::getId))
                .orElseThrow(() -> new PassengerNotFoundByEmailException(email));
    }


    /**
     * для auth service(добавляет пустого пользователя в таблицу пассажиров)
     */
    @Override
    @Transactional
    public PassengerResponseDTO addPassenger(String email, String phone) {
        passengerServiceValidationManagerImpl.checkEmailIsUnique(email);
        passengerServiceValidationManagerImpl.checkPhoneIsUnique(phone);
        return passengerMapper.toDTO(passengerRepository.save(PassengerEntity.builder()
                .name("not specified")
                .email(email)
                .phone(phone)
                .rating(0.0)
                .totalRides(0L)
                .isDeleted(false)
                .build()));
    }

    @Override
    public Page<PassengerResponseDTO> getAllPassengers(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        if(field == null)
            field = "id";
        if(isSortDirectionAsc == null)
            isSortDirectionAsc = true;

        var sortDirection = isSortDirectionAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Page<PassengerEntity> passengerEntityPage = passengerRepository.findAll(
                PageRequest.of(offset, itemCount,
                        Sort.by(sortDirection, field))
        );
        if(!passengerEntityPage.hasContent())
            throw new PassengersNotFoundException();
        return passengerEntityPage.map(passengerMapper::toDTO);
    }
}
