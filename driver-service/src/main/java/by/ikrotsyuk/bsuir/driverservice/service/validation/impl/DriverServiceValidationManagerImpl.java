package by.ikrotsyuk.bsuir.driverservice.service.validation.impl;

import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverWithSameEmailAlreadyExistsException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverWithSamePhoneAlreadyExistsException;
import by.ikrotsyuk.bsuir.driverservice.repository.DriverRepository;
import by.ikrotsyuk.bsuir.driverservice.service.validation.DriverServiceValidationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DriverServiceValidationManagerImpl implements DriverServiceValidationManager {
    private final DriverRepository driverRepository;

    @Override
    @Transactional(readOnly = true)
    public void checkEmailIsUnique(String email) {
        if(driverRepository.existsByEmail(email))
            throw new DriverWithSameEmailAlreadyExistsException(email);
    }

    @Override
    @Transactional(readOnly = true)
    public void checkPhoneIsUnique(String phone) {
        if(driverRepository.existsByPhone(phone))
            throw new DriverWithSamePhoneAlreadyExistsException(phone);
    }
}
