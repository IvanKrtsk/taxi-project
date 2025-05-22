package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.config.TestConfig;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverNotFoundByIdException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverVehiclesNotFoundException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriversNotFoundException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehicleNotBelongToDriverException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehicleNotFoundByIdException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.vehicle.VehicleWithSameLicensePlateAlreadyExistsException;
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
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        when(vehicleService.getAllVehicles(getDEFAULT_PAGE(), getDEFAULT_ITEMS_PER_PAGE_COUNT(), getDEFAULT_SORT_FIELD(), true))
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

        when(vehicleService.getAllVehicles(getDEFAULT_PAGE(), getDEFAULT_ITEMS_PER_PAGE_COUNT(), getDEFAULT_SORT_FIELD(), true))
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
}
