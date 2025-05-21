package by.ikrotsyuk.bsuir.driverservice.service.validation.impl;

import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverWithSameEmailAlreadyExistsException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverWithSamePhoneAlreadyExistsException;
import by.ikrotsyuk.bsuir.driverservice.repository.DriverRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DriverServiceValidationManagerImplTest {
    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverServiceValidationManagerImpl driverServiceValidationManagerImpl;

    @Test
    void checkEmailIsUnique_ThrowsDriverWithSameEmailAlreadyExistsException(){
        when(driverRepository.existsByEmail(anyString()))
                .thenReturn(true);

        assertThatThrownBy(() ->
                driverServiceValidationManagerImpl.checkEmailIsUnique(anyString())
        ).isInstanceOf(DriverWithSameEmailAlreadyExistsException.class);

        verify(driverRepository)
                .existsByEmail(anyString());
    }

    @Test
    void checkEmailIsUnique_Success(){
        when(driverRepository.existsByEmail(anyString()))
                .thenReturn(false);

        assertDoesNotThrow(() ->
                driverServiceValidationManagerImpl.checkEmailIsUnique(anyString())
        );

        verify(driverRepository)
                .existsByEmail(anyString());
    }

    @Test
    void checkPhoneIsUnique_ThrowsDriverWithSamePhoneAlreadyExistsException(){
        when(driverRepository.existsByPhone(anyString()))
                .thenReturn(true);

        assertThatThrownBy(() ->
                driverServiceValidationManagerImpl.checkPhoneIsUnique(anyString())
        ).isInstanceOf(DriverWithSamePhoneAlreadyExistsException.class);

        verify(driverRepository)
                .existsByPhone(anyString());
    }

    @Test
    void checkPhoneIsUnique_Success(){
        when(driverRepository.existsByPhone(anyString()))
                .thenReturn(false);

        assertDoesNotThrow(() ->
                driverServiceValidationManagerImpl.checkPhoneIsUnique(anyString())
        );

        verify(driverRepository)
                .existsByPhone(anyString());
    }
}
