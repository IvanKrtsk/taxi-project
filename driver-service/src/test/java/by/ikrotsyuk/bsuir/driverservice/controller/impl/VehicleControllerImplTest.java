package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.config.TestConfig;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverCurrentVehicleNotFoundException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverNotFoundByIdException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverVehiclesNotFoundException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehicleNotBelongToDriverException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehicleNotFoundByIdException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehicleNotFoundByLicensePlateException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehicleWithSameLicensePlateAlreadyExistsException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehiclesNotFoundByBrandException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehiclesNotFoundByTypeException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehiclesNotFoundByYearException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehiclesNotFoundException;
import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.keys.VehicleExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.service.VehicleService;
import by.ikrotsyuk.bsuir.driverservice.utils.LocaleUtils;
import by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.ADD_VEHICLE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.CHOOSE_CURRENT_VEHICLE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.DELETE_VEHICLE_BY_LICENSE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_ALL_DRIVER_VEHICLES;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_ALL_VEHICLES;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_ALL_VEHICLES_BY_BRAND;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_ALL_VEHICLES_BY_TYPE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_ALL_VEHICLES_BY_YEAR;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_DRIVER_CURRENT_VEHICLE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_VEHICLE_BY_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_VEHICLE_BY_LICENSE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.UPDATE_VEHICLE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.getObjectsPage;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleControllerImpl.class)
@Import(TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VehicleControllerImplTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @MockitoBean
    private VehicleService vehicleService;

    private final VehicleRequestDTO vehicleRequestDTO = TestDataGenerator.getVehicleRequestDTO();
    private final VehicleResponseDTO vehicleResponseDTO = TestDataGenerator.getVehicleResponseDTO(true);

    private Set<Locale> supportedLocales;

    @BeforeAll
    void initSupportedLocales() throws IOException {
        LocaleUtils localeUtils = new LocaleUtils(resourcePatternResolver);
        supportedLocales = localeUtils.getSupportedLocales();
    }

    Stream<String> supportedLocales() {
        return supportedLocales.stream()
                .map(Locale::toLanguageTag);
    }

    private MockHttpServletRequestBuilder withLocale(MockHttpServletRequestBuilder requestBuilder, Locale locale) {
        return requestBuilder.header("Accept-Language", locale.toLanguageTag());
    }

    @Test
    void addVehicle_ReturnsVehicleResponseDTO() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(ADD_VEHICLE, vehicleResponseDTO.driverId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleRequestDTO));

        when(vehicleService.addVehicle(anyLong(), any(VehicleRequestDTO.class)))
                .thenReturn(vehicleResponseDTO);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(vehicleResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void addVehicle_ThrowsDriverNotFoundByIdException(String locale) throws Exception{
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(ADD_VEHICLE, vehicleResponseDTO.driverId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleRequestDTO));

        when(vehicleService.addVehicle(anyLong(), any(VehicleRequestDTO.class)))
                .thenThrow(new DriverNotFoundByIdException(vehicleResponseDTO.driverId()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{vehicleResponseDTO.driverId()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void addVehicle_ThrowsVehicleWithSameLicensePlateAlreadyExistsException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(ADD_VEHICLE, vehicleResponseDTO.driverId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleRequestDTO));

        when(vehicleService.addVehicle(anyLong(), any(VehicleRequestDTO.class)))
                .thenThrow(new VehicleWithSameLicensePlateAlreadyExistsException(vehicleResponseDTO.licensePlate()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(VehicleExceptionMessageKeys.VEHICLE_WITH_SAME_LICENSE_PLATE_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), new Object[]{vehicleResponseDTO.licensePlate()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void editVehicle_ReturnsVehicleResponseDTO() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch(UPDATE_VEHICLE, vehicleResponseDTO.driverId(), vehicleResponseDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleRequestDTO));

        when(vehicleService.editVehicle(anyLong(), anyLong(), any(VehicleRequestDTO.class)))
                .thenReturn(vehicleResponseDTO);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(vehicleResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void editVehicle_ThrowsDriverNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch(UPDATE_VEHICLE, vehicleResponseDTO.driverId(), vehicleResponseDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleRequestDTO));

        when(vehicleService.editVehicle(anyLong(), anyLong(), any(VehicleRequestDTO.class)))
                .thenThrow(new DriverNotFoundByIdException(vehicleResponseDTO.driverId()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{vehicleResponseDTO.driverId()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void editVehicle_ThrowsVehicleNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch(UPDATE_VEHICLE, vehicleResponseDTO.driverId(), vehicleResponseDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleRequestDTO));

        when(vehicleService.editVehicle(anyLong(), anyLong(), any(VehicleRequestDTO.class)))
                .thenThrow(new VehicleNotFoundByIdException(vehicleResponseDTO.id()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(VehicleExceptionMessageKeys.VEHICLE_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{vehicleResponseDTO.id()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void editVehicle_ThrowsVehicleNotBelongToDriverException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch(UPDATE_VEHICLE, vehicleResponseDTO.driverId(), vehicleResponseDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleRequestDTO));

        when(vehicleService.editVehicle(anyLong(), anyLong(), any(VehicleRequestDTO.class)))
                .thenThrow(new VehicleNotBelongToDriverException(vehicleResponseDTO.id(), vehicleResponseDTO.driverId()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messageKey").value(VehicleExceptionMessageKeys.VEHICLE_NOT_BELONG_TO_DRIVER_MESSAGE_KEY.getMessageKey()))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void editVehicle_ThrowsVehicleWithSameLicensePlateAlreadyExistsException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch(UPDATE_VEHICLE, vehicleResponseDTO.driverId(), vehicleResponseDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleRequestDTO));

        when(vehicleService.editVehicle(anyLong(), anyLong(), any(VehicleRequestDTO.class)))
                .thenThrow(new VehicleWithSameLicensePlateAlreadyExistsException(vehicleResponseDTO.licensePlate()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(VehicleExceptionMessageKeys.VEHICLE_WITH_SAME_LICENSE_PLATE_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), new Object[]{vehicleResponseDTO.licensePlate()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void chooseCurrentVehicle_ReturnsVehicleResponseDTO() throws Exception {
        when(vehicleService.chooseCurrentVehicle(anyLong(), anyLong()))
                .thenReturn(vehicleResponseDTO);

        mockMvc.perform(patch(CHOOSE_CURRENT_VEHICLE, vehicleResponseDTO.driverId(), vehicleResponseDTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(vehicleResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void chooseCurrentVehicle_ThrowsDriverNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(vehicleService.chooseCurrentVehicle(anyLong(), anyLong()))
                .thenThrow(new DriverNotFoundByIdException(vehicleResponseDTO.driverId()));

        mockMvc.perform(withLocale(patch(CHOOSE_CURRENT_VEHICLE, vehicleResponseDTO.driverId(), vehicleResponseDTO.id()), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{vehicleResponseDTO.driverId()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void chooseCurrentVehicle_ThrowsVehicleNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(vehicleService.chooseCurrentVehicle(anyLong(), anyLong()))
                .thenThrow(new VehicleNotFoundByIdException(vehicleResponseDTO.id()));

        mockMvc.perform(withLocale(patch(CHOOSE_CURRENT_VEHICLE, vehicleResponseDTO.driverId(), vehicleResponseDTO.id()), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(VehicleExceptionMessageKeys.VEHICLE_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{vehicleResponseDTO.id()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void chooseCurrentVehicle_ThrowsVehicleNotBelongToDriverException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(vehicleService.chooseCurrentVehicle(anyLong(), anyLong()))
                .thenThrow(new VehicleNotBelongToDriverException(vehicleResponseDTO.id(), vehicleResponseDTO.driverId()));

        mockMvc.perform(withLocale(patch(CHOOSE_CURRENT_VEHICLE, vehicleResponseDTO.driverId(), vehicleResponseDTO.id()), currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messageKey").value(VehicleExceptionMessageKeys.VEHICLE_NOT_BELONG_TO_DRIVER_MESSAGE_KEY.getMessageKey()))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void chooseCurrentVehicle_ThrowsDriverVehiclesNotFoundException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(vehicleService.chooseCurrentVehicle(anyLong(), anyLong()))
                .thenThrow(new DriverVehiclesNotFoundException(vehicleResponseDTO.driverId()));

        mockMvc.perform(withLocale(patch(CHOOSE_CURRENT_VEHICLE, vehicleResponseDTO.driverId(), vehicleResponseDTO.id()), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_VEHICLES_NOT_FOUND_MESSAGE_KEY.getMessageKey(), new Object[]{vehicleResponseDTO.driverId()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void getVehicleById_ReturnsVehicleResponseDTO() throws Exception {
        when(vehicleService.getVehicleById(anyLong()))
                .thenReturn(vehicleResponseDTO);

        mockMvc.perform(get(GET_VEHICLE_BY_ID, vehicleResponseDTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(vehicleResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getVehicleById_ThrowsVehicleNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(vehicleService.getVehicleById(anyLong()))
                .thenThrow(new VehicleNotFoundByIdException(vehicleResponseDTO.id()));

        mockMvc.perform(withLocale(get(GET_VEHICLE_BY_ID, vehicleResponseDTO.id()), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(VehicleExceptionMessageKeys.VEHICLE_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{vehicleResponseDTO.id()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void getAllVehicles_ReturnPageOfVehicleResponseDTO() throws Exception {
        when(vehicleService.getAllVehicles(TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true))
                .thenReturn(getObjectsPage(vehicleResponseDTO));

        mockMvc.perform(get(GET_ALL_VEHICLES)
                .param("offset", String.valueOf(TestDataGenerator.getDEFAULT_PAGE()))
                .param("itemCount", String.valueOf(TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT()))
                .param("field", TestDataGenerator.getDEFAULT_SORT_FIELD())
                .param("isSortDirectionAsc", "true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()").value(1));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getAllVehicles_ThrowsVehiclesNotFoundException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(vehicleService.getAllVehicles(TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true))
                .thenThrow(new VehiclesNotFoundException());

        mockMvc.perform(withLocale(get(GET_ALL_VEHICLES), currentLocale)
                .param("offset", String.valueOf(TestDataGenerator.getDEFAULT_PAGE()))
                .param("itemCount", String.valueOf(TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT()))
                .param("field", TestDataGenerator.getDEFAULT_SORT_FIELD())
                .param("isSortDirectionAsc", "true"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(VehicleExceptionMessageKeys.VEHICLES_NOT_FOUND_MESSAGE_KEY.getMessageKey(), new Object[]{}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void getAllVehiclesByType_ReturnsPageOfVehicleResponseDTO() throws Exception {
        when(vehicleService.getAllVehiclesByType(vehicleResponseDTO.carClass(), TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true))
                .thenReturn(getObjectsPage(vehicleResponseDTO));

        mockMvc.perform(get(GET_ALL_VEHICLES_BY_TYPE)
                .param("type", vehicleResponseDTO.carClass().name())
                .param("offset", String.valueOf(TestDataGenerator.getDEFAULT_PAGE()))
                .param("itemCount", String.valueOf(TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT()))
                .param("field", TestDataGenerator.getDEFAULT_SORT_FIELD())
                .param("isSortDirectionAsc", "true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()").value(1));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getAllVehiclesByType_ThrowsVehiclesNotFoundByTypeException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(vehicleService.getAllVehiclesByType(vehicleResponseDTO.carClass(), TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true))
                .thenThrow(new VehiclesNotFoundByTypeException(vehicleResponseDTO.carClass()));

        mockMvc.perform(withLocale(get(GET_ALL_VEHICLES_BY_TYPE), currentLocale)
                .param("type", vehicleResponseDTO.carClass().name())
                .param("offset", String.valueOf(TestDataGenerator.getDEFAULT_PAGE()))
                .param("itemCount", String.valueOf(TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT()))
                .param("field", TestDataGenerator.getDEFAULT_SORT_FIELD())
                .param("isSortDirectionAsc", "true"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messageKey").value(VehicleExceptionMessageKeys.VEHICLE_NOT_FOUND_BY_TYPE_MESSAGE_KEY.getMessageKey()))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void getAllVehiclesByYear_ReturnsPageOfVehicleResponseDTO() throws Exception {
        when(vehicleService.getAllVehiclesByYear(vehicleResponseDTO.year(), TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true))
                .thenReturn(getObjectsPage(vehicleResponseDTO));

        mockMvc.perform(get(GET_ALL_VEHICLES_BY_YEAR)
                .param("year", String.valueOf(vehicleResponseDTO.year()))
                .param("offset", String.valueOf(TestDataGenerator.getDEFAULT_PAGE()))
                .param("itemCount", String.valueOf(TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT()))
                .param("field", TestDataGenerator.getDEFAULT_SORT_FIELD())
                .param("isSortDirectionAsc", "true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()").value(1));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getAllVehiclesByYear_ThrowsVehiclesNotFoundByTypeException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(vehicleService.getAllVehiclesByYear(vehicleResponseDTO.year(), TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true))
                .thenThrow(new VehiclesNotFoundByYearException(vehicleResponseDTO.year()));

        mockMvc.perform(withLocale(get(GET_ALL_VEHICLES_BY_YEAR), currentLocale)
                .param("year", String.valueOf(vehicleResponseDTO.year()))
                .param("offset", String.valueOf(TestDataGenerator.getDEFAULT_PAGE()))
                .param("itemCount", String.valueOf(TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT()))
                .param("field", TestDataGenerator.getDEFAULT_SORT_FIELD())
                .param("isSortDirectionAsc", "true"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messageKey").value(VehicleExceptionMessageKeys.VEHICLE_NOT_FOUND_BY_YEAR_MESSAGE_KEY.getMessageKey()))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void getAllVehiclesByBrand_ReturnsPageOfVehicleResponseDTO() throws Exception {
        when(vehicleService.getAllVehiclesByBrand(vehicleResponseDTO.brand(), TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true))
                .thenReturn(getObjectsPage(vehicleResponseDTO));

        mockMvc.perform(get(GET_ALL_VEHICLES_BY_BRAND)
                .param("brand", String.valueOf(vehicleResponseDTO.brand()))
                .param("offset", String.valueOf(TestDataGenerator.getDEFAULT_PAGE()))
                .param("itemCount", String.valueOf(TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT()))
                .param("field", TestDataGenerator.getDEFAULT_SORT_FIELD())
                .param("isSortDirectionAsc", "true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()").value(1));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getAllVehiclesByBrand_ThrowsVehiclesNotFoundByTypeException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(vehicleService.getAllVehiclesByBrand(vehicleResponseDTO.brand(), TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true))
                .thenThrow(new VehiclesNotFoundByBrandException(vehicleResponseDTO.brand()));

        mockMvc.perform(withLocale(get(GET_ALL_VEHICLES_BY_BRAND), currentLocale)
                .param("brand", String.valueOf(vehicleResponseDTO.brand()))
                .param("offset", String.valueOf(TestDataGenerator.getDEFAULT_PAGE()))
                .param("itemCount", String.valueOf(TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT()))
                .param("field", TestDataGenerator.getDEFAULT_SORT_FIELD())
                .param("isSortDirectionAsc", "true"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messageKey").value(VehicleExceptionMessageKeys.VEHICLE_NOT_FOUND_BY_BRAND_MESSAGE_KEY.getMessageKey()))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void getAllVehiclesByLicensePlate_ReturnsPageOfVehicleResponseDTO() throws Exception {
        when(vehicleService.getVehicleByLicense(vehicleResponseDTO.licensePlate()))
                .thenReturn(vehicleResponseDTO);

        mockMvc.perform(get(GET_VEHICLE_BY_LICENSE)
                .param("licensePlate", String.valueOf(vehicleResponseDTO.licensePlate())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(vehicleResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getAllVehiclesByLicensePlate_ThrowsVehiclesNotFoundByTypeException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(vehicleService.getVehicleByLicense(vehicleResponseDTO.licensePlate()))
                .thenThrow(new VehicleNotFoundByLicensePlateException(vehicleResponseDTO.licensePlate()));

        mockMvc.perform(withLocale(get(GET_VEHICLE_BY_LICENSE), currentLocale)
                .param("licensePlate", String.valueOf(vehicleResponseDTO.licensePlate())))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.messageKey").value(VehicleExceptionMessageKeys.VEHICLE_NOT_FOUND_BY_LICENSE_PLATE_MESSAGE_KEY.getMessageKey()))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void deleteVehicleById_ReturnsVehicleResponseDTO() throws Exception {
        when(vehicleService.deleteVehicleById(anyLong(), anyLong()))
                .thenReturn(vehicleResponseDTO);

        mockMvc.perform(delete(DELETE_VEHICLE_BY_LICENSE, vehicleResponseDTO.driverId(), vehicleResponseDTO.id()))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(vehicleResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void deleteVehicleById_ThrowsVehicleNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(vehicleService.deleteVehicleById(anyLong(), anyLong()))
                .thenThrow(new VehicleNotFoundByIdException(vehicleResponseDTO.id()));

        mockMvc.perform(withLocale(delete(DELETE_VEHICLE_BY_LICENSE, vehicleResponseDTO.driverId(), vehicleResponseDTO.id()), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(VehicleExceptionMessageKeys.VEHICLE_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{vehicleResponseDTO.id()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void getAllDriverVehicles_ReturnsListOfVehicleResponseDTO() throws Exception {
        when(vehicleService.getAllDriverVehicles(anyLong()))
                .thenReturn(List.of(vehicleResponseDTO));

        mockMvc.perform(get(GET_ALL_DRIVER_VEHICLES, vehicleResponseDTO.driverId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(vehicleResponseDTO))));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getAllDriverVehicles_ThrowsDriverNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(vehicleService.getAllDriverVehicles(anyLong()))
                .thenThrow(new DriverNotFoundByIdException(vehicleResponseDTO.driverId()));

        mockMvc.perform(withLocale(get(GET_ALL_DRIVER_VEHICLES, vehicleResponseDTO.driverId()), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{vehicleResponseDTO.driverId()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getAllDriverVehicles_ThrowsDriverVehiclesNotFoundException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(vehicleService.getAllDriverVehicles(anyLong()))
                .thenThrow(new DriverVehiclesNotFoundException(vehicleResponseDTO.driverId()));

        mockMvc.perform(withLocale(get(GET_ALL_DRIVER_VEHICLES, vehicleResponseDTO.driverId()), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_VEHICLES_NOT_FOUND_MESSAGE_KEY.getMessageKey(), new Object[]{vehicleResponseDTO.driverId()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void getDriverCurrentVehicle_ReturnsDriverResponseDTO() throws Exception {
        when(vehicleService.getDriverCurrentVehicle(anyLong()))
                .thenReturn(vehicleResponseDTO);

        mockMvc.perform(get(GET_DRIVER_CURRENT_VEHICLE, vehicleResponseDTO.driverId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(vehicleResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getDriverCurrentVehicle_ThrowsDriverCurrentVehicleNotFoundException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(vehicleService.getDriverCurrentVehicle(anyLong()))
                .thenThrow(new DriverCurrentVehicleNotFoundException(vehicleResponseDTO.driverId()));

        mockMvc.perform(withLocale(get(GET_DRIVER_CURRENT_VEHICLE, vehicleResponseDTO.driverId()), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_CURRENT_VEHICLE_NOT_FOUND.getMessageKey(), new Object[]{vehicleResponseDTO.driverId()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }
}
