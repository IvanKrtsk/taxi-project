package by.ikrotsyuk.bsuir.passengerservice.service.impl;

import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.passengerservice.entity.PassengerEntity;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerNotFoundByEmailException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerNotFoundByIdException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerWithSameEmailAlreadyExistsException;
import by.ikrotsyuk.bsuir.passengerservice.mapper.PassengerMapper;
import by.ikrotsyuk.bsuir.passengerservice.repository.PassengerRepository;
import by.ikrotsyuk.bsuir.passengerservice.service.PassengerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Override
    public PassengerResponseDTO getPassengerById(Long id){
        Optional<PassengerEntity> optionalPassengerEntity = passengerRepository.findById(id);
        return optionalPassengerEntity.map(passengerMapper::toDTO).orElseThrow(() -> new PassengerNotFoundByIdException(id));
    }

    @Override
    public Double getPassengerRatingById(Long id){
        return this.getPassengerById(id).getRating();
    }

    /**
     * активирует смену email в keycloak
     */
    @Override
    @Transactional
    public PassengerResponseDTO editPassengerProfile(Long id, PassengerRequestDTO passengerRequestDTO) {
        Optional<PassengerEntity> optionalPassengerEntity = passengerRepository.findById(id);
        if(optionalPassengerEntity.isPresent()){
            PassengerEntity passengerEntity = optionalPassengerEntity.get();
            String email = passengerRequestDTO.getEmail();
            if(!passengerEntity.getEmail().equals(email)){
                passengerEntity.setEmail(email);
                // activates keycloak email change
            }
            passengerEntity.setName(passengerRequestDTO.getName());
            passengerEntity.setPhone(passengerRequestDTO.getPhone());
            return passengerMapper.toDTO(passengerEntity);
        } else{
            throw new PassengerNotFoundByIdException(id);
        }

    }

    @Override
    @Transactional
    public PassengerResponseDTO deletePassengerProfile(Long id) {
        Optional<PassengerEntity> optionalPassengerEntity = passengerRepository.findById(id);
        if(optionalPassengerEntity.isPresent()) {
            PassengerEntity passengerEntity = optionalPassengerEntity.get();
            passengerEntity.setIsDeleted(true);
            return passengerMapper.toDTO(passengerEntity);
        } else
            throw new PassengerNotFoundByIdException(id);
    }

    /**
     * для auth service(проверяет соответствие email и id)
     */
    @Override
    public Long checkIsEmailCorrect(Long id, String email) {
        return passengerRepository.findById(id)
                .filter(passengerEntity -> passengerEntity.getEmail().equals(email))
                .map(PassengerEntity::getId)
                .or(() -> passengerRepository.findByEmail(email).map(PassengerEntity::getId))
                .orElseThrow(() -> new PassengerNotFoundByIdException(id));
    }


    /**
     * для auth service(добавляет пустого пользователя в таблицу пассажиров)
     */
    @Override
    @Transactional
    public Boolean addPassenger(String email) {
        Optional<PassengerEntity> optionalPassengerEntity = passengerRepository.findByEmail(email);
        if(optionalPassengerEntity.isEmpty()) {
            passengerRepository.save(PassengerEntity.builder()
                    .name("not specified")
                    .email(email)
                    .phone("not specified")
                    .rating(0.0)
                    .totalRides(0L)
                    .isDeleted(false)
                    .build());
            return true;
        } else
            throw new PassengerWithSameEmailAlreadyExistsException(email);
    }
}
