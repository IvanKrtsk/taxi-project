package by.ikrotsyuk.bsuir.passengerservice.service.impl;

import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.passengerservice.entity.PassengerEntity;
import by.ikrotsyuk.bsuir.passengerservice.entity.customtypes.PaymentTypeTypesPassenger;
import by.ikrotsyuk.bsuir.passengerservice.entity.customtypes.StatusTypesPassenger;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.*;
import by.ikrotsyuk.bsuir.passengerservice.mapper.PassengerMapper;
import by.ikrotsyuk.bsuir.passengerservice.repository.PassengerRepository;
import by.ikrotsyuk.bsuir.passengerservice.service.PassengerService;
import by.ikrotsyuk.bsuir.passengerservice.service.tools.PaginationTool;
import by.ikrotsyuk.bsuir.passengerservice.service.validation.impl.PassengerServiceValidationManagerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final PassengerServiceValidationManagerImpl passengerServiceValidationManagerImpl;
    private final PaginationTool paginationTool;


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
    public PassengerResponseDTO addPassenger(PassengerRequestDTO passengerRequestDTO) {
        String email = passengerRequestDTO.email();
        String phone = passengerRequestDTO.phone();
        if(passengerRepository.existsByEmail(email)){
            PassengerEntity passengerEntity = passengerRepository.findByEmail(email)
                    .orElseThrow(() -> new PassengerNotFoundByEmailException(email));
            if(passengerEntity.getIsDeleted()) {
                passengerEntity.setIsDeleted(true);
                return passengerMapper.toDTO(passengerEntity);
            } else
                throw new PassengerWithSameEmailAlreadyExistsException(email);
        } else {
            passengerServiceValidationManagerImpl.checkEmailIsUnique(email);
            passengerServiceValidationManagerImpl.checkPhoneIsUnique(phone);
            return passengerMapper.toDTO(passengerRepository.save(PassengerEntity.builder()
                    .name("not specified")
                    .email(email)
                    .phone(phone)
                    .rating(0.0)
                    .totalRides(0L)
                    .isDeleted(false)
                    .status(StatusTypesPassenger.AVAILABLE)
                    .paymentType(PaymentTypeTypesPassenger.CASH)
                    .build()));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PassengerResponseDTO> getAllPassengers(int offset, int itemCount, String field, Boolean isSortDirectionAsc) {
        Page<PassengerEntity> passengerEntityPage = passengerRepository.findAll(
                paginationTool.getPageRequest(offset, itemCount, field, isSortDirectionAsc));
        if(!passengerEntityPage.hasContent())
            throw new PassengersNotFoundException();
        return passengerEntityPage.map(passengerMapper::toDTO);
    }

    @Override
    @Transactional
    public PassengerResponseDTO changePaymentType(Long id, PaymentTypeTypesPassenger paymentType) {
        PassengerEntity passengerEntity = passengerRepository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundByIdException(id));

        passengerEntity.setPaymentType(paymentType);
        return passengerMapper.toDTO(passengerEntity);
    }
}
