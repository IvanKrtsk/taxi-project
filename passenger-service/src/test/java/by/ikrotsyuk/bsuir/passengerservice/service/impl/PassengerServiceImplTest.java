package by.ikrotsyuk.bsuir.passengerservice.service.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.passengerservice.entity.PassengerEntity;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.FeignConnectException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerAlreadyDeletedException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerNotFoundByEmailException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerNotFoundByIdException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerWithSameEmailAlreadyExistsException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengersNotFoundException;
import by.ikrotsyuk.bsuir.passengerservice.feign.PassengerAccountClient;
import by.ikrotsyuk.bsuir.passengerservice.mapper.PassengerMapper;
import by.ikrotsyuk.bsuir.passengerservice.repository.PassengerRepository;
import by.ikrotsyuk.bsuir.passengerservice.service.utils.PaginationUtil;
import by.ikrotsyuk.bsuir.passengerservice.service.validation.PassengerServiceValidationManager;
import by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.PASSENGER_PAYMENT_TYPE_CARD;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.getCleanPassengerEntity;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.getCleanPassengerResponseDTO;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.getDeletedPassengerEntity;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.getDeletedPassengerResponseDTO;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.getPassengerResponseDTOWithChangedPaymentType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceImplTest {
    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerMapper passengerMapper;

    @Mock
    private PassengerServiceValidationManager passengerServiceValidationManager;

    @Mock
    private PaginationUtil paginationUtil;

    @Mock
    private PassengerAccountClient passengerAccountClient;

    @InjectMocks
    private PassengerServiceImpl passengerServiceImpl;

    private final PassengerEntity passengerEntity = TestDataGenerator.getPassengerEntity();
    private final PassengerRequestDTO passengerRequestDTO = TestDataGenerator.getPassengerRequestDTO();
    private final PassengerRequestDTO customPassengerRequestDTO = TestDataGenerator.getCustomPassengerRequestDTO();
    private final PassengerResponseDTO passengerResponseDTO = TestDataGenerator.getPassengerResponseDTO();
    private final Page<PassengerEntity> passengerEntityPage = TestDataGenerator.getObjectsPage(passengerEntity);
    private final Pageable pageRequest = TestDataGenerator.getPageRequest();
    private PassengerResponseDTO deletedPassengerResponseDTO;
    private PassengerEntity deletedPassengerEntity;
    private PassengerEntity cleanPassengerEntity;
    private PassengerResponseDTO cleanPassengerResponseDTO;

    private final String NEEDS_CLEAN_PASSENGER_TAG = "NEEDS_CLEAN_PASSENGER_TAG";
    private final String NEEDS_DELETED_PASSENGER_TAG = "NEEDS_DELETED_PASSENGER_TAG";

    @BeforeEach
    void setUp(TestInfo testInfo) {
        var tags = testInfo.getTags();
        if(tags.contains(NEEDS_DELETED_PASSENGER_TAG)) {
            deletedPassengerResponseDTO = getDeletedPassengerResponseDTO();
            deletedPassengerEntity = getDeletedPassengerEntity();
        }
        if(tags.contains(NEEDS_CLEAN_PASSENGER_TAG)) {
            cleanPassengerEntity = getCleanPassengerEntity();
            cleanPassengerResponseDTO = getCleanPassengerResponseDTO();
        }
    }

    @Test
    void getPassengerById_ReturnsPassengerResponseDTO() {
        when(passengerRepository.findById(anyLong()))
                .thenReturn(Optional.of(passengerEntity));
        when(passengerMapper.toDTO(any(PassengerEntity.class)))
                .thenReturn(passengerResponseDTO);

        var dto = passengerServiceImpl.getPassengerById(anyLong());

        assertThat(dto)
                .isNotNull()
                .isEqualTo(passengerResponseDTO);

        verify(passengerRepository)
                .findById(anyLong());
        verify(passengerMapper)
                .toDTO(any(PassengerEntity.class));
    }

    @Test
    void getPassengerById_ThrowsPassengerNotFoundByIdException() {
        when(passengerRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                passengerServiceImpl.getPassengerById(anyLong())
        ).isInstanceOf(PassengerNotFoundByIdException.class);

        verify(passengerRepository)
                .findById(anyLong());
    }

    @Test
    void getPassengerRatingById_ReturnsDouble() {
        when(passengerRepository.findById(anyLong()))
                .thenReturn(Optional.of(passengerEntity));
        when(passengerMapper.toDTO(any(PassengerEntity.class)))
                .thenReturn(passengerResponseDTO);

        var rating = passengerServiceImpl.getPassengerRatingById(anyLong());

        assertThat(rating)
                .isNotNull()
                .isEqualTo(passengerEntity.getRating());

        verify(passengerRepository)
                .findById(anyLong());
        verify(passengerMapper)
                .toDTO(any(PassengerEntity.class));
    }

    @Test
    void editPassengerProfile_ReturnsPassengerResponseDTO() {
        when(passengerRepository.findById(anyLong()))
                .thenReturn(Optional.of(passengerEntity));
        doNothing()
                .when(passengerServiceValidationManager).checkEmailIsUnique(anyString());
        doNothing()
                .when(passengerServiceValidationManager).checkPhoneIsUnique(anyString());
        when(passengerMapper.toDTO(any(PassengerEntity.class)))
                .thenReturn(passengerResponseDTO);

        var dto = passengerServiceImpl.editPassengerProfile(passengerEntity.getId(), customPassengerRequestDTO);

        assertThat(dto)
                .isNotNull()
                .isEqualTo(passengerResponseDTO);

        verify(passengerRepository)
                .findById(anyLong());
        verify(passengerServiceValidationManager)
                .checkEmailIsUnique(anyString());
        verify(passengerServiceValidationManager)
                .checkPhoneIsUnique(anyString());
        verify(passengerMapper)
                .toDTO(any(PassengerEntity.class));
    }

    @Test
    void editPassengerProfile_ThrowsPassengerNotFoundByIdException() {
        when(passengerRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                passengerServiceImpl.editPassengerProfile(passengerEntity.getId(), customPassengerRequestDTO)
        ).isInstanceOf(PassengerNotFoundByIdException.class);

        verify(passengerRepository)
                .findById(anyLong());
    }

    @Test
    @Tag(NEEDS_DELETED_PASSENGER_TAG)
    void deletePassengerProfile_ReturnsPassengerResponseDTO() {
        when(passengerRepository.findById(anyLong()))
                .thenReturn(Optional.of(passengerEntity));
        when(passengerMapper.toDTO(passengerEntity))
                .thenReturn(deletedPassengerResponseDTO);

        var dto = passengerServiceImpl.deletePassengerProfile(anyLong());

        assertThat(dto)
                .isNotNull()
                .isEqualTo(deletedPassengerResponseDTO);

        verify(passengerRepository)
                .findById(anyLong());
        verify(passengerMapper)
                .toDTO(any(PassengerEntity.class));
    }

    @Test
    void deletePassengerProfile_ThrowsPassengerNotFoundByIdException() {
        when(passengerRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                passengerServiceImpl.deletePassengerProfile(anyLong())
        ).isInstanceOf(PassengerNotFoundByIdException.class);

        verify(passengerRepository)
                .findById(anyLong());
    }

    @Test
    @Tag(NEEDS_DELETED_PASSENGER_TAG)
    void deletePassengerProfile_ThrowsPassengerAlreadyDeletedException() {
        when(passengerRepository.findById(anyLong()))
                .thenReturn(Optional.of(deletedPassengerEntity));

        assertThatThrownBy(() ->
                passengerServiceImpl.deletePassengerProfile(anyLong())
        ).isInstanceOf(PassengerAlreadyDeletedException.class);

        verify(passengerRepository)
                .findById(anyLong());
    }

    @Test
    @Tag(NEEDS_DELETED_PASSENGER_TAG)
    void addPassengerWhenAlreadyExist_ReturnsPassengerResponseDTO() {
        when(passengerRepository.existsByEmail(anyString()))
                .thenReturn(true);
        when(passengerRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(deletedPassengerEntity));
        when(passengerMapper.toDTO(deletedPassengerEntity))
                .thenReturn(passengerResponseDTO);

        var dto = passengerServiceImpl.addPassenger(passengerRequestDTO);

        assertThat(dto)
                .isNotNull()
                .isEqualTo(passengerResponseDTO);

        verify(passengerRepository)
                .existsByEmail(anyString());
        verify(passengerRepository)
                .findByEmail(anyString());
        verify(passengerMapper)
                .toDTO(any(PassengerEntity.class));
    }

    @Test
    void addPassengerWhenAlreadyExist_ThrowsPassengerNotFoundByEmailException() {
        when(passengerRepository.existsByEmail(anyString()))
                .thenReturn(true);
        when(passengerRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                passengerServiceImpl.addPassenger(passengerRequestDTO)
        ).isInstanceOf(PassengerNotFoundByEmailException.class);

        verify(passengerRepository)
                .existsByEmail(anyString());
        verify(passengerRepository)
                .findByEmail(anyString());
    }

    @Test
    void addPassengerWhenAlreadyExist_ThrowsPassengerWithSameEmailAlreadyExistsException() {
        when(passengerRepository.existsByEmail(anyString()))
                .thenReturn(true);
        when(passengerRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(passengerEntity));

        assertThatThrownBy(() ->
                passengerServiceImpl.addPassenger(passengerRequestDTO)
        ).isInstanceOf(PassengerWithSameEmailAlreadyExistsException.class);

        verify(passengerRepository)
                .existsByEmail(anyString());
        verify(passengerRepository)
                .findByEmail(anyString());
    }

    @Test
    @Tag(NEEDS_CLEAN_PASSENGER_TAG)
    void addPassenger_ReturnsPassengerResponseDTO() {
        when(passengerRepository.existsByEmail(anyString()))
                .thenReturn(false);
        when(passengerRepository.save(any(PassengerEntity.class)))
                .thenReturn(cleanPassengerEntity);
        when(passengerAccountClient.createAccount(anyLong(), any(AccountTypes.class)))
                .thenReturn(new ResponseEntity<>(1L, HttpStatus.CREATED));
        when(passengerMapper.toDTO(cleanPassengerEntity))
                .thenReturn(cleanPassengerResponseDTO);

        var dto = passengerServiceImpl.addPassenger(passengerRequestDTO);

        assertThat(dto)
                .isNotNull()
                .isEqualTo(cleanPassengerResponseDTO);

        verify(passengerRepository)
                .existsByEmail(anyString());
        verify(passengerRepository)
                .save(any(PassengerEntity.class));
        verify(passengerAccountClient)
                .createAccount(anyLong(), any(AccountTypes.class));
        verify(passengerMapper)
                .toDTO(any(PassengerEntity.class));
    }

    @Test
    @Tag(NEEDS_CLEAN_PASSENGER_TAG)
    void addPassenger_ThrowsFeignConnectException() {
        when(passengerRepository.existsByEmail(anyString()))
                .thenReturn(false);
        when(passengerRepository.save(any(PassengerEntity.class)))
                .thenReturn(cleanPassengerEntity);
        when(passengerAccountClient.createAccount(anyLong(), any(AccountTypes.class)))
                .thenThrow(feign.RetryableException.class);

        assertThatThrownBy(() ->
                passengerServiceImpl.addPassenger(passengerRequestDTO)
        ).isInstanceOf(FeignConnectException.class);

        verify(passengerRepository)
                .existsByEmail(anyString());
        verify(passengerRepository)
                .save(any(PassengerEntity.class));
        verify(passengerAccountClient)
                .createAccount(anyLong(), any(AccountTypes.class));
    }

    @Test
    void getAllPassengers_ReturnsPageOfPassengerResponseDTO() {
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean()))
                .thenReturn(pageRequest);
        when(passengerRepository.findAll(any(Pageable.class)))
                .thenReturn(passengerEntityPage);
        when(passengerMapper.toDTO(any(PassengerEntity.class)))
                .thenAnswer(invocation -> passengerResponseDTO);

        Page<PassengerResponseDTO> result = passengerServiceImpl.getAllPassengers(TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true);

        assertThat(result)
                .isNotNull();
        assertThat(result.getContent())
                .hasSize(1);
        assertThat(result.getContent().getFirst().name())
                .isEqualTo(passengerResponseDTO.name());

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean());
        verify(passengerRepository)
                .findAll(any(Pageable.class));
        verify(passengerMapper)
                .toDTO(any(PassengerEntity.class));
    }

    @Test
    void getAllPassengers_ThrowsPassengersNotFoundException() {
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean()))
                .thenReturn(pageRequest);
        when(passengerRepository.findAll(any(Pageable.class)))
                .thenReturn(Page.empty());

        assertThatThrownBy(() ->
                passengerServiceImpl.getAllPassengers(TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true)
        ).isInstanceOf(PassengersNotFoundException.class);

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean());
        verify(passengerRepository)
                .findAll(any(Pageable.class));
    }

    @Test
    void changePaymentType_ReturnsPassengerResponseDTO() {
        PassengerResponseDTO responseDTO = getPassengerResponseDTOWithChangedPaymentType();

        when(passengerRepository.findById(anyLong()))
                .thenReturn(Optional.of(passengerEntity));
        when(passengerMapper.toDTO(any(PassengerEntity.class)))
                .thenReturn(responseDTO);

        var dto = passengerServiceImpl.changePaymentType(passengerEntity.getId(), PASSENGER_PAYMENT_TYPE_CARD);

        assertThat(dto)
                .isNotNull()
                .isEqualTo(responseDTO);

        verify(passengerRepository)
                .findById(anyLong());
        verify(passengerMapper)
                .toDTO(any(PassengerEntity.class));
    }

    @Test
    void changePaymentType_ThrowsPassengerNotFoundByIdException() {
        when(passengerRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                passengerServiceImpl.changePaymentType(passengerEntity.getId(), PASSENGER_PAYMENT_TYPE_CARD)
        ).isInstanceOf(PassengerNotFoundByIdException.class);

        verify(passengerRepository)
                .findById(anyLong());
    }
}
