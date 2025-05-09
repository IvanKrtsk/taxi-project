package by.ikrotsyuk.bsuir.passengerservice.service.validation.impl;

import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerWithSameEmailAlreadyExistsException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerWithSamePhoneAlreadyExistsException;
import by.ikrotsyuk.bsuir.passengerservice.repository.PassengerRepository;
import by.ikrotsyuk.bsuir.passengerservice.service.validation.PassengerServiceValidationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PassengerServiceValidationManagerImpl implements PassengerServiceValidationManager {
    private final PassengerRepository passengerRepository;

    @Override
    @Transactional(readOnly = true)
    public void checkEmailIsUnique(String email) {
        if(passengerRepository.existsByEmail(email))
            throw new PassengerWithSameEmailAlreadyExistsException(email);
    }

    @Override
    @Transactional(readOnly = true)
    public void checkPhoneIsUnique(String phone) {
        if(passengerRepository.existsByPhone(phone))
            throw new PassengerWithSamePhoneAlreadyExistsException(phone);
    }
}
