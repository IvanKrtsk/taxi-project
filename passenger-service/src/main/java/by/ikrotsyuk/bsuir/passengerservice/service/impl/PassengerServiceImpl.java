package by.ikrotsyuk.bsuir.passengerservice.service.impl;

import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.passengerservice.entity.PassengerEntity;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerAlreadyDeletedException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerNotFoundByEmailException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerNotFoundByIdException;
import by.ikrotsyuk.bsuir.passengerservice.exception.keys.PassengerExceptionMessageKeys;
import by.ikrotsyuk.bsuir.passengerservice.mapper.PassengerMapper;
import by.ikrotsyuk.bsuir.passengerservice.repository.PassengerRepository;
import by.ikrotsyuk.bsuir.passengerservice.service.PassengerService;
import by.ikrotsyuk.bsuir.passengerservice.service.validation.impl.PassengerServiceValidationManagerImpl;
import lombok.RequiredArgsConstructor;
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
               .orElseThrow(() -> new PassengerNotFoundByIdException(PassengerExceptionMessageKeys.PASSENGER_NOT_FOUND_BY_ID_MESSAGE_KEY, id));
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
                .orElseThrow(() -> new PassengerNotFoundByIdException(PassengerExceptionMessageKeys.PASSENGER_NOT_FOUND_BY_ID_MESSAGE_KEY, id));
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
                .orElseThrow(() -> new PassengerNotFoundByIdException(PassengerExceptionMessageKeys.PASSENGER_NOT_FOUND_BY_ID_MESSAGE_KEY, id));
        if(passengerEntity.getIsDeleted())
            throw new PassengerAlreadyDeletedException(PassengerExceptionMessageKeys.PASSENGER_ALREADY_DELETED_MESSAGE_KEY, id);
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
                .orElseThrow(() -> new PassengerNotFoundByEmailException(PassengerExceptionMessageKeys.PASSENGER_NOT_FOUND_BY_EMAIL_MESSAGE_KEY, email));
    }


    /**
     * для auth service(добавляет пустого пользователя в таблицу пассажиров)
     */
    @Override
    @Transactional
    public PassengerResponseDTO addPassenger(String email) {
        passengerServiceValidationManagerImpl.checkEmailIsUnique(email);
        return passengerMapper.toDTO(passengerRepository.save(PassengerEntity.builder()
                .name("not specified")
                .email(email)
                .phone("not specified")
                .rating(0.0)
                .totalRides(0L)
                .isDeleted(false)
                .build()));
    }
}
