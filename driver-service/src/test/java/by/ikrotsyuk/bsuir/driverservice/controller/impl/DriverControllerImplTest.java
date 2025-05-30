package by.ikrotsyuk.bsuir.driverservice.controller.impl;

import by.ikrotsyuk.bsuir.driverservice.config.TestConfig;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverVehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.FeignConnectException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverAlreadyDeletedException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverNotFoundByEmailException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverNotFoundByIdException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverWithSameEmailAlreadyExistsException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriverWithSamePhoneAlreadyExistsException;
import by.ikrotsyuk.bsuir.driverservice.exception.exceptions.driver.DriversNotFoundException;
import by.ikrotsyuk.bsuir.driverservice.exception.keys.DriverExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.driverservice.service.DriverService;
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

import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.ADD_DRIVER;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.DELETE_DRIVER_BY_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_ALL_DRIVERS;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_DRIVER_PROFILE_BY_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_DRIVER_RATING_BY_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_DRIVER_WITH_VEHICLES;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_DIRECTION;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_DIRECTION_VALUE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_FIELD;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_ITEMS;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_OFFSET;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.UPDATE_DRIVER_BY_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.getObjectsPage;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DriverControllerImpl.class)
@Import(TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DriverControllerImplTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @MockitoBean
    private DriverService driverService;

    private final DriverResponseDTO driverResponseDTO = TestDataGenerator.getDriverResponseDTO();
    private final DriverRequestDTO driverRequestDTO = TestDataGenerator.getDriverRequestDTO();
    private final DriverVehicleResponseDTO driverVehicleResponseDTO = TestDataGenerator.getDriverVehicleResponseDTO();

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
    void getDriverProfile_ReturnsDriverResponseDTO() throws Exception {
        when(driverService.getDriverProfileById(anyLong()))
                .thenReturn(driverResponseDTO);

        mockMvc.perform(get(GET_DRIVER_PROFILE_BY_ID, driverResponseDTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(driverResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getDriverProfileById_ThrowsDriverNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(driverService.getDriverProfileById(anyLong()))
                .thenThrow(new DriverNotFoundByIdException(anyLong()));

        mockMvc.perform(withLocale(get(GET_DRIVER_PROFILE_BY_ID, driverResponseDTO.id()), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{0L}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void getDriverRating_ReturnsDouble() throws Exception {
        when(driverService.getDriverRatingById(anyLong()))
                .thenReturn(driverResponseDTO.rating());

        mockMvc.perform(get(GET_DRIVER_RATING_BY_ID, driverResponseDTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(driverResponseDTO.rating())));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getDriverRating_ThrowsDriverNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(driverService.getDriverRatingById(anyLong()))
                .thenThrow(new DriverNotFoundByIdException(anyLong()));

        mockMvc.perform(withLocale(get(GET_DRIVER_RATING_BY_ID, driverResponseDTO.id()), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{0L}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void editDriverProfile_ReturnsDriverResponseDTO() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(UPDATE_DRIVER_BY_ID, driverResponseDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driverRequestDTO));

        when(driverService.editDriverProfile(anyLong(), any(DriverRequestDTO.class)))
                .thenReturn(driverResponseDTO);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(driverResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void editDriverProfile_ThrowsDriverNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(UPDATE_DRIVER_BY_ID, driverResponseDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driverRequestDTO));

        when(driverService.editDriverProfile(anyLong(), any(DriverRequestDTO.class)))
                .thenThrow(new DriverNotFoundByIdException(0L));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{0L}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void editDriverProfile_ThrowsDriverWithSameEmailAlreadyExistsException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(UPDATE_DRIVER_BY_ID, driverResponseDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driverRequestDTO));

        when(driverService.editDriverProfile(anyLong(), any(DriverRequestDTO.class)))
                .thenThrow(new DriverWithSameEmailAlreadyExistsException(driverResponseDTO.email()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_WITH_SAME_EMAIL_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), new Object[]{driverResponseDTO.email()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void editDriverProfile_ThrowsDriverWithSamePhoneAlreadyExistsException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(UPDATE_DRIVER_BY_ID, driverResponseDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driverRequestDTO));

        when(driverService.editDriverProfile(anyLong(), any(DriverRequestDTO.class)))
                .thenThrow(new DriverWithSamePhoneAlreadyExistsException(driverResponseDTO.phone()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_WITH_SAME_PHONE_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), new Object[]{driverResponseDTO.phone()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void deleteDriverProfile_ReturnsDriverResponseDTO() throws Exception {
        when(driverService.deleteDriverProfile(anyLong()))
                .thenReturn(driverResponseDTO);

        mockMvc.perform(delete(DELETE_DRIVER_BY_ID, driverResponseDTO.id()))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(driverResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void deleteDriverProfile_ThrowsDriverNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(driverService.deleteDriverProfile(anyLong()))
                .thenThrow(new DriverNotFoundByIdException(driverResponseDTO.id()));

        mockMvc.perform(withLocale(delete(DELETE_DRIVER_BY_ID, driverResponseDTO.id()), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{driverResponseDTO.id()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void deleteDriverProfile_ThrowsDriverAlreadyDeletedException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(driverService.deleteDriverProfile(anyLong()))
                .thenThrow(new DriverAlreadyDeletedException(driverResponseDTO.id()));

        mockMvc.perform(withLocale(delete(DELETE_DRIVER_BY_ID, driverResponseDTO.id()), currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_ALREADY_DELETED_MESSAGE_KEY.getMessageKey(), new Object[]{driverResponseDTO.id()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void getAllDrivers_ReturnsPageOfDriverResponseDTO() throws Exception {
        when(driverService.getAllDrivers(TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true))
                .thenReturn(getObjectsPage(driverResponseDTO));

        mockMvc.perform(get(GET_ALL_DRIVERS)
                .param(REQUEST_PARAM_OFFSET, String.valueOf(TestDataGenerator.getDEFAULT_PAGE()))
                .param(REQUEST_PARAM_ITEMS, String.valueOf(TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT()))
                .param(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .param(REQUEST_PARAM_DIRECTION, REQUEST_PARAM_DIRECTION_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.length()").value(1));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getAllDrivers_ThrowsDriversNotFoundException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(driverService.getAllDrivers(TestDataGenerator.getDEFAULT_PAGE(), TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT(), TestDataGenerator.getDEFAULT_SORT_FIELD(), true))
                .thenThrow(new DriversNotFoundException());

        mockMvc.perform(withLocale(get(GET_ALL_DRIVERS), currentLocale)
                .param(REQUEST_PARAM_OFFSET, String.valueOf(TestDataGenerator.getDEFAULT_PAGE()))
                .param(REQUEST_PARAM_ITEMS, String.valueOf(TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT()))
                .param(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .param(REQUEST_PARAM_DIRECTION, REQUEST_PARAM_DIRECTION_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVERS_NOT_FOUND_MESSAGE_KEY.getMessageKey(), new Object[]{}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void addDriver_ReturnsDriverResponseDTO() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(ADD_DRIVER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driverRequestDTO));

        when(driverService.addDriver(any(DriverRequestDTO.class)))
                .thenReturn(driverResponseDTO);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(driverResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void addDriver_ThrowsDriverNotFoundByEmailException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(ADD_DRIVER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driverRequestDTO));

        when(driverService.addDriver(any(DriverRequestDTO.class)))
                .thenThrow(new DriverNotFoundByEmailException(driverRequestDTO.email()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_EMAIL_MESSAGE_KEY.getMessageKey(), new Object[]{driverResponseDTO.email()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void addDriver_ThrowsDriverWithSameEmailAlreadyExistsException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(ADD_DRIVER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driverRequestDTO));

        when(driverService.addDriver(any(DriverRequestDTO.class)))
                .thenThrow(new DriverWithSameEmailAlreadyExistsException(driverRequestDTO.email()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_WITH_SAME_EMAIL_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), new Object[]{driverResponseDTO.email()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void addDriver_ThrowsFeignConnectException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(ADD_DRIVER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(driverRequestDTO));

        when(driverService.addDriver(any(DriverRequestDTO.class)))
                .thenThrow(new FeignConnectException());

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(GeneralExceptionMessageKeys.FEIGN_CONNECT_MESSAGE_KEY.getMessageKey(), new Object[]{}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void getDriverWithVehicle_ReturnsDriverVehicleResponseDTO() throws Exception {
        when(driverService.getDriverWithVehicleById(anyLong()))
                .thenReturn(driverVehicleResponseDTO);

        mockMvc.perform(get(GET_DRIVER_WITH_VEHICLES, driverVehicleResponseDTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(driverVehicleResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getDriverWithVehicle_ThrowsDriverNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(driverService.getDriverWithVehicleById(anyLong()))
                .thenThrow(new DriverNotFoundByIdException(driverVehicleResponseDTO.id()));

        mockMvc.perform(withLocale(get(GET_DRIVER_WITH_VEHICLES, driverVehicleResponseDTO.id()), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(DriverExceptionMessageKeys.DRIVER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{driverVehicleResponseDTO.id()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }
}
