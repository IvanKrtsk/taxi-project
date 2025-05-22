package by.ikrotsyuk.bsuir.driverservice.service.impl;

import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverCurrentVehicleNotFoundException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverNotFoundByIdException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverVehiclesNotFoundException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehicleNotBelongToDriverException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehicleNotFoundByIdException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehicleNotFoundByLicensePlateException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehiclesNotFoundByBrandException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehiclesNotFoundByTypeException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehiclesNotFoundByYearException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehiclesNotFoundException;
import by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import by.ikrotsyuk.bsuir.driverservice.mapper.VehicleMapper;
import by.ikrotsyuk.bsuir.driverservice.repository.DriverRepository;
import by.ikrotsyuk.bsuir.driverservice.repository.VehicleRepository;
import by.ikrotsyuk.bsuir.driverservice.service.utils.PaginationUtil;
import by.ikrotsyuk.bsuir.driverservice.service.validation.impl.VehicleServiceValidationManagerImpl;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceImplTest {
    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleMapper vehicleMapper;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private VehicleServiceValidationManagerImpl vehicleServiceValidationManagerImpl;

    @Mock
    private PaginationUtil paginationUtil;

    @InjectMocks
    private VehicleServiceImpl vehicleServiceImpl;

    private VehicleEntity vehicleEntity;
    private VehicleRequestDTO vehicleRequestDTO;
    private VehicleResponseDTO vehicleResponseDTO;
    private DriverEntity driverEntity;
    private Page<VehicleEntity> vehicleEntityPage;
    private Pageable pageRequest;

    private final String REQUEST_TAG = "REQUEST";
    private final String UNCURRENT_TAG = "UNCURRENT";
    private final String DRIVER_TAG = "DRIVER";
    private final String PAGINATION_TAG = "PAGINATION";

    @BeforeEach
    void setUp(TestInfo testInfo){
        vehicleEntity = TestDataGenerator.getVehicleEntity();
        if(testInfo.getTags().contains(REQUEST_TAG))
            vehicleRequestDTO = TestDataGenerator.getVehicleRequestDTO();
        boolean isCurrent = !testInfo.getTags().contains(UNCURRENT_TAG);
        vehicleResponseDTO = TestDataGenerator.getVehicleResponseDTO(isCurrent);
        if(testInfo.getTags().contains(DRIVER_TAG)) {
            driverEntity = TestDataGenerator.getDriverEntity();
            vehicleEntity.setDriver(driverEntity);
        }
        if(testInfo.getTags().contains(PAGINATION_TAG)) {
            vehicleEntityPage = TestDataGenerator.getObjectsPage(vehicleEntity);
            pageRequest = TestDataGenerator.getPageRequest();
        }
    }

    @Test
    @Tag(UNCURRENT_TAG)
    void getDriverCurrentVehicle_ThrowsDriverCurrentVehicleNotFoundException(){
        vehicleEntity.setIsCurrent(false);
        DriverEntity mockDriverEntity = mock(DriverEntity.class);

        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.of(mockDriverEntity));
        when(mockDriverEntity.getDriverVehicles())
                .thenReturn(List.of(vehicleEntity));
        when(vehicleMapper.toDTOList(List.of(vehicleEntity)))
                .thenReturn(List.of(vehicleResponseDTO));
        when(vehicleServiceImpl.getAllDriverVehicles(anyLong()))
                .thenReturn(List.of(vehicleResponseDTO));

        assertThatThrownBy(() ->
                vehicleServiceImpl.getDriverCurrentVehicle(anyLong())
        ).isInstanceOf(DriverCurrentVehicleNotFoundException.class);

        verify(driverRepository, times(2))
                .findById(anyLong());
        verify(mockDriverEntity, times(2))
                .getDriverVehicles();
        verify(vehicleMapper)
                .toDTOList(List.of(vehicleEntity));
    }

    @Test
    void getDriverCurrentVehicle_ReturnsVehicleResponseDTO(){
        DriverEntity mockDriverEntity = mock(DriverEntity.class);
        List<VehicleResponseDTO> vehicleResponseDTOList = List.of(vehicleResponseDTO);

        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.of(mockDriverEntity));
        when(mockDriverEntity.getDriverVehicles())
                .thenReturn(List.of(vehicleEntity));
        when(vehicleMapper.toDTOList(List.of(vehicleEntity)))
                .thenReturn(vehicleResponseDTOList);
        when(vehicleServiceImpl.getAllDriverVehicles(anyLong()))
                .thenReturn(vehicleResponseDTOList);

        var result = vehicleServiceImpl.getDriverCurrentVehicle(anyLong());

        assertThat(result)
                .isEqualTo(vehicleResponseDTO);

        verify(driverRepository, times(2))
                .findById(anyLong());
        verify(mockDriverEntity, times(2))
                .getDriverVehicles();
        verify(vehicleMapper)
                .toDTOList(List.of(vehicleEntity));
    }

    @Test
    void getAllDriverVehicles_ThrowsDriverNotFoundByIdException(){
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                vehicleServiceImpl.getAllDriverVehicles(anyLong())
        ).isInstanceOf(DriverNotFoundByIdException.class);

        verify(driverRepository)
                .findById(anyLong());
    }

    @Test
    void getAllDriverVehicles_ThrowsDriverVehiclesNotFoundException(){
        DriverEntity mockDriverEntity = mock(DriverEntity.class);

        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.of(mockDriverEntity));
        when(mockDriverEntity.getDriverVehicles())
                .thenReturn(List.of());

        assertThatThrownBy(() ->
                vehicleServiceImpl.getAllDriverVehicles(anyLong())
        ).isInstanceOf(DriverVehiclesNotFoundException.class);

        verify(driverRepository)
                .findById(anyLong());
        verify(mockDriverEntity)
                .getDriverVehicles();
    }

    @Test
    void getAllDriverVehicles_ReturnsListOfVehicleResponseDTO(){
        DriverEntity mockDriverEntity = mock(DriverEntity.class);
        List<VehicleEntity> vehicleEntityList = List.of(vehicleEntity);
        List<VehicleResponseDTO> vehicleResponseDTOList = List.of(vehicleResponseDTO);

        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.of(mockDriverEntity));
        when(mockDriverEntity.getDriverVehicles())
                .thenReturn(vehicleEntityList);
        when(vehicleMapper.toDTOList(vehicleEntityList))
                .thenReturn(vehicleResponseDTOList);

        var result = vehicleServiceImpl.getAllDriverVehicles(anyLong());

        assertThat(result)
                .hasSize(1)
                .isEqualTo(vehicleResponseDTOList);

        verify(driverRepository)
                .findById(anyLong());
        verify(mockDriverEntity)
                .getDriverVehicles();
        verify(vehicleMapper)
                .toDTOList(vehicleEntityList);
    }

    @Test
    void checkDriverAndVehicleId_ThrowsDriverNotFoundByIdException(){
        when(driverRepository.existsById(anyLong()))
                .thenReturn(false);

        assertThatThrownBy(() ->
                vehicleServiceImpl.checkDriverAndVehicleId(1L, 1L)
        ).isInstanceOf(DriverNotFoundByIdException.class);

        verify(driverRepository)
                .existsById(anyLong());
    }

    @Test
    void checkDriverAndVehicleId_ThrowsVehicleNotFoundByIdException(){
        when(driverRepository.existsById(anyLong()))
                .thenReturn(true);
        when(vehicleRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                vehicleServiceImpl.checkDriverAndVehicleId(1L, 1L)
        ).isInstanceOf(VehicleNotFoundByIdException.class);

        verify(driverRepository)
                .existsById(anyLong());
        verify(vehicleRepository)
                .findById(anyLong());
    }

    @Test
    @Tag(DRIVER_TAG)
    void checkDriverAndVehicleId_ThrowsVehicleNotBelongToDriverException(){
        driverEntity.setId(2L);
        when(driverRepository.existsById(anyLong()))
                .thenReturn(true);
        when(vehicleRepository.findById(anyLong()))
                .thenReturn(Optional.of(vehicleEntity));

        assertThatThrownBy(() ->
                vehicleServiceImpl.checkDriverAndVehicleId(1L, 1L)
        ).isInstanceOf(VehicleNotBelongToDriverException.class);

        verify(driverRepository)
                .existsById(anyLong());
        verify(vehicleRepository)
                .findById(anyLong());
    }

    @Test
    @Tag(DRIVER_TAG)
    void checkDriverAndVehicleId_Success(){
        when(driverRepository.existsById(anyLong()))
                .thenReturn(true);
        when(vehicleRepository.findById(anyLong()))
                .thenReturn(Optional.of(vehicleEntity));

        assertDoesNotThrow(() ->
                vehicleServiceImpl.checkDriverAndVehicleId(1L, 1L)
        );

        verify(driverRepository)
                .existsById(anyLong());
        verify(vehicleRepository)
                .findById(anyLong());
    }

    @Test
    void deleteVehicleById_ThrowsVehicleNotFoundByIdException(){
        VehicleServiceImpl spyVehicleService = spy(vehicleServiceImpl);
        doNothing().when(spyVehicleService).checkDriverAndVehicleId(anyLong(), anyLong());

        when(vehicleRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                spyVehicleService.deleteVehicleById(1L, 1L)
        ).isInstanceOf(VehicleNotFoundByIdException.class);

        verify(vehicleRepository)
                .findById(anyLong());
    }

    @Test
    void deleteVehicleById_ReturnsVehicleResponseDTO(){
        VehicleServiceImpl spyVehicleService = spy(vehicleServiceImpl);
        doNothing()
                .when(spyVehicleService)
                .checkDriverAndVehicleId(anyLong(), anyLong());

        when(vehicleRepository.findById(anyLong()))
                .thenReturn(Optional.of(vehicleEntity));
        when(vehicleMapper.toDTO(vehicleEntity))
                .thenReturn(vehicleResponseDTO);
        doNothing()
                .when(vehicleRepository).deleteById(anyLong());

        var result = spyVehicleService.deleteVehicleById(anyLong(), anyLong());

        assertThat(result)
                .isEqualTo(vehicleResponseDTO);

        verify(vehicleRepository)
                .findById(anyLong());
        verify(vehicleMapper)
                .toDTO(vehicleEntity);
        verify(vehicleRepository)
                .deleteById(anyLong());
    }

    @Test
    void getVehicleByLicense_ThrowsVehicleNotFoundByLicensePlateException(){
        when(vehicleRepository.findByLicensePlate(anyString()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                vehicleServiceImpl.getVehicleByLicense(anyString())
        ).isInstanceOf(VehicleNotFoundByLicensePlateException.class);

        verify(vehicleRepository)
                .findByLicensePlate(anyString());
    }

    @Test
    void getVehicleByLicense_ReturnsVehicleResponseDTO(){
        when(vehicleRepository.findByLicensePlate(anyString()))
                .thenReturn(Optional.of(vehicleEntity));
        when(vehicleMapper.toDTO(vehicleEntity))
                .thenReturn(vehicleResponseDTO);

        var result = vehicleServiceImpl.getVehicleByLicense(anyString());

        assertThat(result)
                .isEqualTo(vehicleResponseDTO);

        verify(vehicleRepository)
                .findByLicensePlate(anyString());
        verify(vehicleMapper)
                .toDTO(vehicleEntity);
    }

    @Test
    @Tag(PAGINATION_TAG)
    void getAllVehiclesByBrand_ThrowsVehiclesNotFoundByBrandException(){
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class)))
                .thenReturn(pageRequest);
        when(vehicleRepository.findAllByBrand(vehicleEntity.getBrand(), pageRequest))
                .thenReturn(Page.empty());

        assertThatThrownBy(() ->
                vehicleServiceImpl.getAllVehiclesByBrand(vehicleEntity.getBrand(), TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true)
        ).isInstanceOf(VehiclesNotFoundByBrandException.class);

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class));
        verify(vehicleRepository)
                .findAllByBrand(vehicleEntity.getBrand(), pageRequest);
    }

    @Test
    @Tag(PAGINATION_TAG)
    @Tag(DRIVER_TAG)
    void getAllVehiclesByBrand_ReturnsPageOfVehicleResponseDTO(){
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class)))
                .thenReturn(pageRequest);
        when(vehicleRepository.findAllByBrand(vehicleEntity.getBrand(), pageRequest))
                .thenReturn(vehicleEntityPage);
        when(vehicleMapper.toDTO(any(VehicleEntity.class))).thenAnswer(invocation -> {
            VehicleEntity entity = invocation.getArgument(0);
            return new VehicleResponseDTO( entity.getId(), entity.getBrand(),
                    entity.getModel(), entity.getCarClass(),
                    entity.getRidesCount(), entity.getYear(),
                    entity.getLicensePlate(), entity.getColor(),
                    entity.getDriver().getId(), entity.getIsCurrent()
            );
        });

        Page<VehicleResponseDTO> result = vehicleServiceImpl.getAllVehiclesByBrand(vehicleEntity.getBrand(), (TestDataGenerator.getDEFAULT_PAGE()), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true);

        assertThat(result)
                .isNotNull();
        assertThat(result.getContent())
                .hasSize(1);
        assertThat(result.getContent().getFirst().licensePlate())
                .isEqualTo(vehicleResponseDTO.licensePlate());

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class));
        verify(vehicleRepository)
                .findAllByBrand(vehicleEntity.getBrand(), pageRequest);
        verify(vehicleMapper, times(vehicleEntityPage.getContent().size()))
                .toDTO(any(VehicleEntity.class));
    }

    @Test
    @Tag(PAGINATION_TAG)
    void getAllVehiclesByYear_ThrowsVehiclesNotFoundByYearException(){
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class)))
                .thenReturn(pageRequest);
        when(vehicleRepository.findAllByYear(vehicleEntity.getYear(), pageRequest))
                .thenReturn(Page.empty());

        assertThatThrownBy(() ->
                vehicleServiceImpl.getAllVehiclesByYear(vehicleEntity.getYear(), TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true)
        ).isInstanceOf(VehiclesNotFoundByYearException.class);

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class));
        verify(vehicleRepository)
                .findAllByYear(vehicleEntity.getYear(), pageRequest);
    }

    @Test
    @Tag(PAGINATION_TAG)
    @Tag(DRIVER_TAG)
    void getAllVehiclesByYear_ReturnsPageOfVehicleResponseDTO(){
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class)))
                .thenReturn(pageRequest);
        when(vehicleRepository.findAllByYear(vehicleEntity.getYear(), pageRequest))
                .thenReturn(vehicleEntityPage);
        when(vehicleMapper.toDTO(any(VehicleEntity.class))).thenAnswer(invocation -> {
            VehicleEntity entity = invocation.getArgument(0);
            return new VehicleResponseDTO( entity.getId(), entity.getBrand(),
                    entity.getModel(), entity.getCarClass(),
                    entity.getRidesCount(), entity.getYear(),
                    entity.getLicensePlate(), entity.getColor(),
                    entity.getDriver().getId(), entity.getIsCurrent()
            );
        });

        Page<VehicleResponseDTO> result = vehicleServiceImpl.getAllVehiclesByYear(vehicleEntity.getYear(), (TestDataGenerator.getDEFAULT_PAGE()), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true);

        assertThat(result)
                .isNotNull();
        assertThat(result.getContent())
                .hasSize(1);
        assertThat(result.getContent().getFirst().licensePlate())
                .isEqualTo(vehicleResponseDTO.licensePlate());

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class));
        verify(vehicleRepository)
                .findAllByYear(vehicleEntity.getYear(), pageRequest);
        verify(vehicleMapper, times(vehicleEntityPage.getContent().size()))
                .toDTO(any(VehicleEntity.class));
    }

    @Test
    @Tag(PAGINATION_TAG)
    void getAllVehiclesByType_ThrowsVehiclesNotFoundByTypeException(){
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class)))
                .thenReturn(pageRequest);
        when(vehicleRepository.findAllByCarClass(vehicleEntity.getCarClass(), pageRequest))
                .thenReturn(Page.empty());

        assertThatThrownBy(() ->
                vehicleServiceImpl.getAllVehiclesByType(vehicleEntity.getCarClass(), TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true)
        ).isInstanceOf(VehiclesNotFoundByTypeException.class);

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class));
        verify(vehicleRepository)
                .findAllByCarClass(vehicleEntity.getCarClass(), pageRequest);
    }

    @Test
    @Tag(PAGINATION_TAG)
    @Tag(DRIVER_TAG)
    void getAllVehiclesByType_ReturnsPageOfVehicleResponseDTO(){
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class)))
                .thenReturn(pageRequest);
        when(vehicleRepository.findAllByCarClass(vehicleEntity.getCarClass(), pageRequest))
                .thenReturn(vehicleEntityPage);
        when(vehicleMapper.toDTO(any(VehicleEntity.class))).thenAnswer(invocation -> {
            VehicleEntity entity = invocation.getArgument(0);
            return new VehicleResponseDTO( entity.getId(), entity.getBrand(),
                    entity.getModel(), entity.getCarClass(),
                    entity.getRidesCount(), entity.getYear(),
                    entity.getLicensePlate(), entity.getColor(),
                    entity.getDriver().getId(), entity.getIsCurrent()
            );
        });

        Page<VehicleResponseDTO> result = vehicleServiceImpl.getAllVehiclesByType(vehicleEntity.getCarClass(), (TestDataGenerator.getDEFAULT_PAGE()), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true);

        assertThat(result)
                .isNotNull();
        assertThat(result.getContent())
                .hasSize(1);
        assertThat(result.getContent().getFirst().licensePlate())
                .isEqualTo(vehicleResponseDTO.licensePlate());

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class));
        verify(vehicleRepository)
                .findAllByCarClass(vehicleEntity.getCarClass(), pageRequest);
        verify(vehicleMapper, times(vehicleEntityPage.getContent().size()))
                .toDTO(any(VehicleEntity.class));
    }

    @Test
    @Tag(PAGINATION_TAG)
    void getAllVehicles_ThrowsVehiclesNotFoundException(){
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class)))
                .thenReturn(pageRequest);
        when(vehicleRepository.findAll(pageRequest))
                .thenReturn(Page.empty());

        assertThatThrownBy(() ->
                vehicleServiceImpl.getAllVehicles(TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true)
        ).isInstanceOf(VehiclesNotFoundException.class);

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class));
        verify(vehicleRepository)
                .findAll(pageRequest);
    }

    @Test
    @Tag(PAGINATION_TAG)
    @Tag(DRIVER_TAG)
    void getAllVehicles_ReturnsPageOfVehicleResponseDTO(){
        when(paginationUtil.getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class)))
                .thenReturn(pageRequest);
        when(vehicleRepository.findAll(pageRequest))
                .thenReturn(vehicleEntityPage);
        when(vehicleMapper.toDTO(any(VehicleEntity.class))).thenAnswer(invocation -> {
            VehicleEntity entity = invocation.getArgument(0);
            return new VehicleResponseDTO( entity.getId(), entity.getBrand(),
                    entity.getModel(), entity.getCarClass(),
                    entity.getRidesCount(), entity.getYear(),
                    entity.getLicensePlate(), entity.getColor(),
                    entity.getDriver().getId(), entity.getIsCurrent()
            );
        });

        Page<VehicleResponseDTO> result = vehicleServiceImpl.getAllVehicles((TestDataGenerator.getDEFAULT_PAGE()), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true);

        assertThat(result)
                .isNotNull();
        assertThat(result.getContent())
                .hasSize(1);
        assertThat(result.getContent().getFirst().licensePlate())
                .isEqualTo(vehicleResponseDTO.licensePlate());

        verify(paginationUtil)
                .getPageRequest(anyInt(), anyInt(), anyString(), anyBoolean(), eq(VehicleEntity.class));
        verify(vehicleRepository)
                .findAll(pageRequest);
        verify(vehicleMapper, times(vehicleEntityPage.getContent().size()))
                .toDTO(any(VehicleEntity.class));
    }

    @Test
    void getVehicleById_ThrowsVehicleNotFoundByIdException(){
        when(vehicleRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
            vehicleServiceImpl.getVehicleById(anyLong())
        ).isInstanceOf(VehicleNotFoundByIdException.class);

        verify(vehicleRepository)
                .findById(anyLong());
    }

    @Test
    void getVehicleById_ReturnsVehicleResponseDTO(){
        when(vehicleRepository.findById(anyLong()))
                .thenReturn(Optional.of(vehicleEntity));
        when(vehicleMapper.toDTO(vehicleEntity))
                .thenReturn(vehicleResponseDTO);

        var result = vehicleServiceImpl.getVehicleById(anyLong());

        assertThat(result)
                .isEqualTo(vehicleResponseDTO);

        verify(vehicleRepository)
                .findById(anyLong());
        verify(vehicleMapper)
                .toDTO(vehicleEntity);
    }

    @Test
    void chooseCurrentVehicle_ThrowsDriverVehiclesNotFoundException(){
        VehicleServiceImpl spyVehicleService = spy(vehicleServiceImpl);
        doNothing()
                .when(spyVehicleService)
                .checkDriverAndVehicleId(anyLong(), anyLong());
        when(vehicleRepository.findAllByDriverId(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                spyVehicleService.chooseCurrentVehicle(anyLong(), anyLong())
        ).isInstanceOf(DriverVehiclesNotFoundException.class);

        verify(spyVehicleService)
                .checkDriverAndVehicleId(anyLong(), anyLong());
        verify(vehicleRepository)
                .findAllByDriverId(anyLong());
    }

    @Test
    void chooseCurrentVehicle_ThrowsVehicleNotFoundByIdException(){
        List<VehicleEntity> vehicleEntityList = List.of(vehicleEntity);
        VehicleServiceImpl spyVehicleService = spy(vehicleServiceImpl);
        doNothing()
                .when(spyVehicleService)
                .checkDriverAndVehicleId(anyLong(), anyLong());
        when(vehicleRepository.findAllByDriverId(anyLong()))
                .thenReturn(Optional.of(vehicleEntityList));
        when(vehicleRepository.saveAll(vehicleEntityList))
                .thenReturn(vehicleEntityList);
        when(vehicleRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                spyVehicleService.chooseCurrentVehicle(anyLong(), anyLong())
        ).isInstanceOf(VehicleNotFoundByIdException.class);

        verify(spyVehicleService)
                .checkDriverAndVehicleId(anyLong(), anyLong());
        verify(vehicleRepository)
                .findAllByDriverId(anyLong());
        verify(vehicleRepository)
                .saveAll(vehicleEntityList);
        verify(vehicleRepository)
                .findById(anyLong());
    }

    @Test
    void chooseCurrentVehicle_ReturnsVehicleResponseDTO(){
        List<VehicleEntity> vehicleEntityList = List.of(vehicleEntity);
        VehicleServiceImpl spyVehicleService = spy(vehicleServiceImpl);
        doNothing()
                .when(spyVehicleService)
                .checkDriverAndVehicleId(anyLong(), anyLong());
        when(vehicleRepository.findAllByDriverId(anyLong()))
                .thenReturn(Optional.of(vehicleEntityList));
        when(vehicleRepository.saveAll(vehicleEntityList))
                .thenReturn(vehicleEntityList);
        when(vehicleRepository.findById(anyLong()))
                .thenReturn(Optional.of(vehicleEntity));
        when(vehicleMapper.toDTO(vehicleEntity))
                .thenReturn(vehicleResponseDTO);

        var result = spyVehicleService.chooseCurrentVehicle(anyLong(), anyLong());

        assertThat(result)
                .isEqualTo(vehicleResponseDTO);

        verify(spyVehicleService)
                .checkDriverAndVehicleId(anyLong(), anyLong());
        verify(vehicleRepository)
                .findAllByDriverId(anyLong());
        verify(vehicleRepository)
                .saveAll(vehicleEntityList);
        verify(vehicleRepository)
                .findById(anyLong());
        verify(vehicleMapper)
                .toDTO(vehicleEntity);
    }

    @Test
    @Tag(REQUEST_TAG)
    void addVehicle_ThrowsDriverNotFoundByIdException(){
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                vehicleServiceImpl.addVehicle(anyLong(), vehicleRequestDTO)
        ).isInstanceOf(DriverNotFoundByIdException.class);

        verify(driverRepository)
                .findById(anyLong());
    }

    @Test
    @Tag(DRIVER_TAG)
    @Tag(REQUEST_TAG)
    void addVehicle_ReturnsVehicleResponseDTO(){
        when(driverRepository.findById(anyLong()))
                .thenReturn(Optional.of(driverEntity));
        doNothing()
                .when(vehicleServiceValidationManagerImpl).checkLicensePlateIsUnique(anyString());
        when(vehicleRepository.findAllByDriverId(anyLong()))
                .thenReturn(Optional.empty());
        when(vehicleMapper.toEntityWithDefault(vehicleRequestDTO))
                .thenReturn(vehicleEntity);
        when(vehicleRepository.save(any(VehicleEntity.class)))
                .thenReturn(vehicleEntity);
        when(vehicleMapper.toDTO(vehicleEntity))
                .thenReturn(vehicleResponseDTO);

        var result = vehicleServiceImpl.addVehicle(anyLong(), vehicleRequestDTO);

        assertThat(result)
                .isEqualTo(vehicleResponseDTO);

        verify(driverRepository)
                .findById(anyLong());
        verify(vehicleServiceValidationManagerImpl)
                .checkLicensePlateIsUnique(anyString());
        verify(vehicleRepository)
                .findAllByDriverId(anyLong());
        verify(vehicleMapper)
                .toEntityWithDefault(vehicleRequestDTO);
        verify(vehicleRepository)
                .save(vehicleEntity);
        verify(vehicleMapper)
                .toDTO(vehicleEntity);
    }

    @Test
    @Tag(REQUEST_TAG)
    void editVehicle_ThrowsVehicleNotFoundByIdException(){
        VehicleServiceImpl spyVehicleService = spy(vehicleServiceImpl);
        doNothing()
                .when(spyVehicleService)
                .checkDriverAndVehicleId(anyLong(), anyLong());
        doNothing()
                .when(vehicleServiceValidationManagerImpl).checkLicensePlateIsUnique(anyString());
        when(vehicleRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                spyVehicleService.editVehicle(1L, 1L, vehicleRequestDTO)
        ).isInstanceOf(VehicleNotFoundByIdException.class);

        verify(spyVehicleService)
                .checkDriverAndVehicleId(anyLong(), anyLong());
        verify(vehicleServiceValidationManagerImpl)
                .checkLicensePlateIsUnique(anyString());
        verify(vehicleRepository)
                .findById(anyLong());
    }

    @Test
    @Tag(DRIVER_TAG)
    @Tag(REQUEST_TAG)
    void editVehicle_ReturnsVehicleResponseDTO(){
        VehicleServiceImpl spyVehicleService = spy(vehicleServiceImpl);
        doNothing()
                .when(spyVehicleService)
                .checkDriverAndVehicleId(anyLong(), anyLong());
        doNothing()
                .when(vehicleServiceValidationManagerImpl).checkLicensePlateIsUnique(anyString());
        when(vehicleRepository.findById(anyLong()))
                .thenReturn(Optional.of(vehicleEntity));
        when(vehicleMapper.toDTO(vehicleEntity))
                .thenReturn(vehicleResponseDTO);

        var result = spyVehicleService.editVehicle(1L, 1L, vehicleRequestDTO);

        assertThat(result)
                .isEqualTo(vehicleResponseDTO);

        verify(spyVehicleService)
                .checkDriverAndVehicleId(anyLong(), anyLong());
        verify(vehicleServiceValidationManagerImpl)
                .checkLicensePlateIsUnique(anyString());
        verify(vehicleRepository)
                .findById(anyLong());
        verify(vehicleMapper)
                .toDTO(vehicleEntity);
    }
}
