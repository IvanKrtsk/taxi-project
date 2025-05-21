package by.ikrotsyuk.bsuir.driverservice.service.impl;

import by.ikrotsyuk.bsuir.communicationparts.event.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverVehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.FeignConnectException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverAlreadyDeletedException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverNotFoundByEmailException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverNotFoundByIdException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverWithSameEmailAlreadyExistsException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverWithSamePhoneAlreadyExistsException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriversNotFoundException;
import by.ikrotsyuk.bsuir.driverservice.feign.DriverAccountClient;
import by.ikrotsyuk.bsuir.driverservice.mapper.DriverMapper;
import by.ikrotsyuk.bsuir.driverservice.repository.DriverRepository;
import by.ikrotsyuk.bsuir.driverservice.service.utils.PaginationUtil;
import by.ikrotsyuk.bsuir.driverservice.service.validation.impl.DriverServiceValidationManagerImpl;
import feign.Request;
import feign.RetryableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DriverServiceImplTest {
    @Mock
    private DriverRepository driverRepository;

    @Mock
    private DriverServiceValidationManagerImpl driverServiceValidationManagerImpl;

    @Mock
    private PaginationUtil paginationUtil;

    @Mock
    private DriverAccountClient driverAccountClient;

    @Mock
    private DriverMapper driverMapper;

    @InjectMocks
    private DriverServiceImpl driverServiceImpl;

    private DriverEntity driverEntity;
    private DriverResponseDTO driverResponseDTO;
    private DriverRequestDTO driverRequestDTO;
    private VehicleEntity vehicleEntity;
    private Page<DriverEntity> driverEntityPage;
    private Pageable pageRequest;

    private final String PAGINATION_TAG = "PAGINATION";
    private final String VEHICLE_TAG = "VEHICLE";
    private final String DRIVER_REQUEST_TAG = "DRIVER";

    @BeforeEach
    void setUp(TestInfo testInfo){
        driverEntity = TestDataGenerator.getDriverEntity();
        driverResponseDTO = TestDataGenerator.getDriverResponseDTO();
        if(testInfo.getTags().contains(VEHICLE_TAG)) {
            vehicleEntity = TestDataGenerator.getVehicleEntity();
            driverEntity.setDriverVehicles(List.of(vehicleEntity));
        }
        if(testInfo.getTags().contains(PAGINATION_TAG)) {
            driverEntityPage = TestDataGenerator.getEntityPage(driverEntity);
            pageRequest = TestDataGenerator.getPageRequest();
        }
        if(testInfo.getTags().contains(DRIVER_REQUEST_TAG)){
            driverRequestDTO = TestDataGenerator.getDriverRequestDTO();
        }
    }

    @Test
    void getDriverProfileById_ReturnsDriverResponseDTO() {
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.of(driverEntity));
        when(driverMapper.toDTO(driverEntity))
                .thenReturn(driverResponseDTO);

        var dto = driverServiceImpl.getDriverProfileById(anyLong());

        assertThat(dto)
                .isEqualTo(driverResponseDTO);

        verify(driverRepository)
                .findById(anyLong());
        verify(driverMapper)
                .toDTO(driverEntity);
    }

    @Test
    void getDriverProfileById_ThrowsDriverNotFoundByIdException(){
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                driverServiceImpl.getDriverProfileById(driverEntity.getId())
        ).isInstanceOf(DriverNotFoundByIdException.class);

        verify(driverRepository).findById(anyLong());
    }

    @Test
    void getDriverRatingById_ReturnsDouble(){
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.of(driverEntity));
        when(driverMapper.toDTO(driverEntity))
                .thenReturn(driverResponseDTO);
        when(driverServiceImpl.getDriverProfileById(anyLong()))
                .thenReturn(driverResponseDTO);

        var rating = driverServiceImpl.getDriverRatingById(anyLong());

        assertThat(rating)
                .isEqualTo(driverResponseDTO.rating());

        verify(driverRepository, times(2))
                .findById(anyLong());
        verify(driverMapper)
                .toDTO(driverEntity);
    }

    @Test
    @Tag(PAGINATION_TAG)
    void getAllDrivers_ReturnsPageOfDriverResponseDTO(){
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(DriverEntity.class)))
                .thenReturn(pageRequest);
        when(driverRepository.findAll(pageRequest))
                .thenReturn(driverEntityPage);
        when(driverMapper.toDTO(any(DriverEntity.class))).thenAnswer(invocation ->{
            DriverEntity entity = invocation.getArgument(0);
            return new DriverResponseDTO(entity.getId(), entity.getName(), entity.getEmail(),
                    entity.getPhone(), entity.getRating(), entity.getTotalRides(),
                    entity.getIsDeleted(), entity.getStatus());
        });

        Page<DriverResponseDTO> result = driverServiceImpl.getAllDrivers(TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true);

        assertThat(result)
                .isNotNull();
        assertThat(result.getContent())
                .hasSize(1);
        assertThat(result.getContent().getFirst().name())
                .isEqualTo(driverResponseDTO.name());

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(DriverEntity.class));
        verify(driverRepository)
                .findAll(pageRequest);
        verify(driverMapper, times(driverEntityPage.getContent().size()))
                .toDTO(any(DriverEntity.class));
    }

    @Test
    @Tag(PAGINATION_TAG)
    void getAllDrivers_ThrowsDriversNotFoundException(){
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(DriverEntity.class)))
                .thenReturn(pageRequest);
        when(driverRepository.findAll(pageRequest))
                .thenReturn(Page.empty());

        assertThatThrownBy(() ->
                driverServiceImpl.getAllDrivers(TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true)
        ).isInstanceOf(DriversNotFoundException.class);

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(DriverEntity.class));
        verify(driverRepository)
                .findAll(pageRequest);
    }

    @Test
    @Tag(VEHICLE_TAG)
    void getDriverWithVehicleById_ReturnsDriverVehicleResponseDTO(){
        DriverVehicleResponseDTO driverVehicleResponseDTO = TestDataGenerator.getDriverVehicleResponseDTO(List.of(vehicleEntity));

        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.of(driverEntity));
        when(driverMapper.toDVDTO(driverEntity))
                .thenReturn(driverVehicleResponseDTO);

        var result = driverServiceImpl.getDriverWithVehicleById(anyLong());

        assertThat(result)
                .isEqualTo(driverVehicleResponseDTO);

        verify(driverRepository)
                .findById(anyLong());
        verify(driverMapper)
                .toDVDTO(driverEntity);
    }

    @Test
    @Tag(VEHICLE_TAG)
    void getDriverWithVehicleById_ThrowsDriverNotFoundByIdException(){
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                driverServiceImpl.getDriverWithVehicleById(anyLong())
        ).isInstanceOf(DriverNotFoundByIdException.class);

        verify(driverRepository)
                .findById(anyLong());
    }

    @Test
    @Tag(DRIVER_REQUEST_TAG)
    void addDriver_DriverExistsButWasDeletedReturnsDriverResponseDTO(){
        driverEntity.setIsDeleted(true);

        when(driverRepository.existsByEmail(anyString()))
                .thenReturn(true);
        when(driverRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(driverEntity));
        when(driverMapper.toDTO(any(DriverEntity.class)))
                .thenReturn(driverResponseDTO);

        var result = driverServiceImpl.addDriver(driverRequestDTO);

        assertThat(result)
                .isEqualTo(driverResponseDTO);

        verify(driverRepository)
                .existsByEmail(anyString());
        verify(driverRepository)
                .findByEmail(anyString());
        verify(driverMapper)
                .toDTO(any(DriverEntity.class));
    }

    @Test
    @Tag(DRIVER_REQUEST_TAG)
    void addDriver_DriverExistsButNotFoundByEmail(){
        when(driverRepository.existsByEmail(anyString()))
                .thenReturn(true);
        when(driverRepository.findByEmail(anyString()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                driverServiceImpl.addDriver(driverRequestDTO)
        ).isInstanceOf(DriverNotFoundByEmailException.class);

        verify(driverRepository)
                .existsByEmail(anyString());
        verify(driverRepository)
                .findByEmail(anyString());
    }

    @Test
    @Tag(DRIVER_REQUEST_TAG)
    void addDriver_DriverWithSameEmailAlreadyExist(){
        when(driverRepository.existsByEmail(anyString()))
                .thenReturn(true);
        when(driverRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(driverEntity));

        assertThatThrownBy(() ->
                driverServiceImpl.addDriver(driverRequestDTO)
        ).isInstanceOf(DriverWithSameEmailAlreadyExistsException.class);

        verify(driverRepository)
                .existsByEmail(anyString());
        verify(driverRepository)
                .findByEmail(anyString());
    }

    @Test
    @Tag(DRIVER_REQUEST_TAG)
    void addDriver_DriverNotExistButEmailIsNotUniquie(){
        when(driverRepository.existsByEmail(anyString()))
                .thenReturn(false);
        doThrow(new DriverWithSamePhoneAlreadyExistsException(String.format("Driver with phone: %s already exists", driverEntity.getPhone())))
                .when(driverServiceValidationManagerImpl).checkPhoneIsUnique(anyString());

        assertThatThrownBy(() ->
                driverServiceImpl.addDriver(driverRequestDTO)
        ).isInstanceOf(DriverWithSamePhoneAlreadyExistsException.class);

        verify(driverRepository)
                .existsByEmail(anyString());
        verify(driverServiceValidationManagerImpl)
                .checkPhoneIsUnique(anyString());
    }

    @Test
    @Tag(DRIVER_REQUEST_TAG)
    void addDriver_DriverNotExistReturnsDriverResponseDTO(){
        when(driverRepository.existsByEmail(anyString()))
                .thenReturn(false);
        when(driverRepository.save(any(DriverEntity.class)))
                .thenReturn(driverEntity);
        when(driverAccountClient.createAccount(anyLong(), any(AccountTypes.class)))
                .thenReturn(new ResponseEntity<>(1L, HttpStatus.CREATED));
        when(driverMapper.toDTO(driverEntity))
                .thenReturn(driverResponseDTO);

        var result = driverServiceImpl.addDriver(driverRequestDTO);

        assertThat(result)
                .isEqualTo(driverResponseDTO);

        verify(driverRepository)
                .existsByEmail(anyString());
        verify(driverRepository)
                .save(any(DriverEntity.class));
        verify(driverAccountClient)
                .createAccount(anyLong(), any(AccountTypes.class));
        verify(driverMapper)
                .toDTO(any(DriverEntity.class));
    }

    @Test
    @Tag(DRIVER_REQUEST_TAG)
    void addDriver_DriverNotExostsButThrowFeignConnectException(){
        when(driverRepository.existsByEmail(anyString()))
                .thenReturn(false);
        when(driverRepository.save(any(DriverEntity.class)))
                .thenReturn(driverEntity);
        doThrow(new RetryableException(
                500,
                "Feign client failed",
                Request.HttpMethod.POST,
                new Date(),
                Request.create(Request.HttpMethod.POST, "/api/v1/users/1/payments/accounts", Map.of(), null, Charset.defaultCharset())
        )).when(driverAccountClient).createAccount(anyLong(), any(AccountTypes.class));

        assertThatThrownBy(() ->
                driverServiceImpl.addDriver(driverRequestDTO)
        ).isInstanceOf(FeignConnectException.class);

        verify(driverRepository)
                .existsByEmail(anyString());
        verify(driverRepository)
                .save(any(DriverEntity.class));
        verify(driverAccountClient)
                .createAccount(anyLong(), any(AccountTypes.class));
    }

    @Test
    void deleteDriverProfile_ThrowsDriverNotFoundByIdException(){
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                driverServiceImpl.deleteDriverProfile(anyLong())
        ).isInstanceOf(DriverNotFoundByIdException.class);

        verify(driverRepository)
                .findById(anyLong());
    }

    @Test
    void deleteDriverProfile_ThrowsDriverAlreadyDeletedException(){
        driverEntity.setIsDeleted(true);

        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.of(driverEntity));

        assertThatThrownBy(() ->
                driverServiceImpl.deleteDriverProfile(anyLong())
        ).isInstanceOf(DriverAlreadyDeletedException.class);

        verify(driverRepository)
                .findById(anyLong());
    }

    @Test
    void deleteDriverProfile_ReturnsDriverResponseDTO(){
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.of(driverEntity));
        when(driverMapper.toDTO(driverEntity))
                .thenReturn(driverResponseDTO);

        var result = driverServiceImpl.deleteDriverProfile(anyLong());

        assertThat(result)
                .isEqualTo(driverResponseDTO);

        verify(driverRepository)
                .findById(anyLong());
        verify(driverMapper)
                .toDTO(driverEntity);
    }

    @Test
    @Tag(DRIVER_REQUEST_TAG)
    void editDriverProfile_ThrowsDriverNotFoundByIdException(){
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                driverServiceImpl.editDriverProfile(anyLong(), driverRequestDTO)
        ).isInstanceOf(DriverNotFoundByIdException.class);

        verify(driverRepository)
                .findById(anyLong());
    }

    @Test
    @Tag(DRIVER_REQUEST_TAG)
    void editDriverProfile_ThrowsDriverWithSameEmailAlreadyExistsException(){
        driverEntity.setEmail(driverEntity.getEmail() + " ");
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.of(driverEntity));
        doThrow(new DriverWithSameEmailAlreadyExistsException(String.format("Driver with email: %s already exists", driverEntity.getEmail())))
                .when(driverServiceValidationManagerImpl).checkEmailIsUnique(anyString());

        assertThatThrownBy(() ->
                driverServiceImpl.editDriverProfile(anyLong(), driverRequestDTO)
        ).isInstanceOf(DriverWithSameEmailAlreadyExistsException.class);

        verify(driverRepository)
                .findById(anyLong());
        verify(driverServiceValidationManagerImpl)
                .checkEmailIsUnique(anyString());
    }

    @Test
    @Tag(DRIVER_REQUEST_TAG)
    void editDriverProfile_ThrowsDriverWithSamePhoneAlreadyExistsException(){
        driverEntity.setPhone(driverEntity.getPhone() + " ");
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.of(driverEntity));
        doThrow(new DriverWithSamePhoneAlreadyExistsException(String.format("Driver with phone: %s already exists", driverEntity.getPhone())))
                .when(driverServiceValidationManagerImpl).checkPhoneIsUnique(anyString());

        assertThatThrownBy(() ->
                driverServiceImpl.editDriverProfile(anyLong(), driverRequestDTO)
        ).isInstanceOf(DriverWithSamePhoneAlreadyExistsException.class);

        verify(driverRepository)
                .findById(anyLong());
        verify(driverServiceValidationManagerImpl)
                .checkPhoneIsUnique(anyString());
    }

    @Test
    @Tag(DRIVER_REQUEST_TAG)
    void editDriverProfileReturnsDriverResponseDTO_ReturnsDriverResponseDTO(){
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.of(driverEntity));
        when(driverMapper.toDTO(driverEntity))
                .thenReturn(driverResponseDTO);

        var result = driverServiceImpl.editDriverProfile(anyLong(), driverRequestDTO);

        assertThat(result)
                .isEqualTo(driverResponseDTO);

        verify(driverRepository)
                .findById(anyLong());
        verify(driverMapper)
                .toDTO(driverEntity);
    }
}
