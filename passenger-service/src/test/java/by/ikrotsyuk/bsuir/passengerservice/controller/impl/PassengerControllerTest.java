package by.ikrotsyuk.bsuir.passengerservice.controller.impl;

import by.ikrotsyuk.bsuir.passengerservice.config.TestConfig;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.passengerservice.entity.customtypes.PaymentTypeTypes;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.FeignConnectException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerAlreadyDeletedException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerNotFoundByEmailException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerNotFoundByIdException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerWithSameEmailAlreadyExistsException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengerWithSamePhoneAlreadyExistsException;
import by.ikrotsyuk.bsuir.passengerservice.exception.exceptions.PassengersNotFoundException;
import by.ikrotsyuk.bsuir.passengerservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.passengerservice.exception.keys.PassengerExceptionMessageKeys;
import by.ikrotsyuk.bsuir.passengerservice.service.PassengerService;
import by.ikrotsyuk.bsuir.passengerservice.utils.LocaleUtils;
import by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator;
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

import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.ADD_PASSENGER;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.DELETE_PASSENGER_PROFILE_BY_ID;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.GET_ALL_PASSENGERS;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.GET_PASSENGER_PROFILE_BY_ID;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.GET_PASSENGER_RATING_BY_ID;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.NON_EXISTENT_ID;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.PASSENGER_PAYMENT_TYPE_CARD;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.REQUEST_PARAM_DIRECTION;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.REQUEST_PARAM_DIRECTION_VALUE;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.REQUEST_PARAM_FIELD;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.REQUEST_PARAM_ITEMS;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.REQUEST_PARAM_OFFSET;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.REQUEST_PARAM_PAYMENT_TYPE;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.UPDATE_PASSENGER_PROFILE_BY_ID;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.UPDATE_PAYMENT_TYPE;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.getDeletedPassengerResponseDTO;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.getObjectsPage;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.getPassengerResponseDTOWithChangedPaymentType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PassengerController.class)
@Import(TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PassengerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @MockitoBean
    private PassengerService passengerService;

    private final PassengerRequestDTO passengerRequestDTO = TestDataGenerator.getPassengerRequestDTO();
    private final PassengerResponseDTO passengerResponseDTO = TestDataGenerator.getPassengerResponseDTO();

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
    void getPassengerProfile_ReturnsPassengerResponseDTO() throws Exception {
        when(passengerService.getPassengerById(anyLong()))
                .thenReturn(passengerResponseDTO);

        mockMvc.perform(get(GET_PASSENGER_PROFILE_BY_ID, passengerResponseDTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(passengerResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getPassengerProfile_ThrowsPassengerNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(passengerService.getPassengerById(anyLong()))
                .thenThrow(new PassengerNotFoundByIdException(anyLong()));

        mockMvc.perform(withLocale(get(GET_PASSENGER_PROFILE_BY_ID, NON_EXISTENT_ID), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(PassengerExceptionMessageKeys.PASSENGER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{0L}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void getPassengerRatingById_ReturnsDouble() throws Exception {
        when(passengerService.getPassengerRatingById(anyLong()))
                .thenReturn(passengerResponseDTO.rating());

        mockMvc.perform(get(GET_PASSENGER_RATING_BY_ID, passengerResponseDTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(passengerResponseDTO.rating())));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getPassengerRatingById_ThrowsPassengerNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(passengerService.getPassengerRatingById(anyLong()))
                .thenThrow(new PassengerNotFoundByIdException(anyLong()));

        mockMvc.perform(withLocale(get(GET_PASSENGER_RATING_BY_ID, NON_EXISTENT_ID), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(PassengerExceptionMessageKeys.PASSENGER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{0L}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void editPassengerProfile_ReturnsPassengerResponseDTO() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(UPDATE_PASSENGER_PROFILE_BY_ID, passengerResponseDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passengerRequestDTO));

        when(passengerService.editPassengerProfile(anyLong(), any(PassengerRequestDTO.class)))
                .thenReturn(passengerResponseDTO);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(passengerResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void editPassengerProfile_ThrowsPassengerNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(UPDATE_PASSENGER_PROFILE_BY_ID, NON_EXISTENT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passengerRequestDTO));

        when(passengerService.editPassengerProfile(anyLong(), any(PassengerRequestDTO.class)))
                .thenThrow(new PassengerNotFoundByIdException(NON_EXISTENT_ID));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(PassengerExceptionMessageKeys.PASSENGER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{NON_EXISTENT_ID}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void editPassengerProfile_ThrowsPassengerWithSameEmailAlreadyExistsException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(UPDATE_PASSENGER_PROFILE_BY_ID, passengerResponseDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passengerRequestDTO));

        when(passengerService.editPassengerProfile(anyLong(), any(PassengerRequestDTO.class)))
                .thenThrow(new PassengerWithSameEmailAlreadyExistsException(passengerRequestDTO.email()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(PassengerExceptionMessageKeys.PASSENGER_WITH_SAME_EMAIL_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), new Object[]{passengerRequestDTO.email()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void editPassengerProfile_ThrowsPassengerWithSamePhoneAlreadyExistsException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(UPDATE_PASSENGER_PROFILE_BY_ID, passengerResponseDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passengerRequestDTO));

        when(passengerService.editPassengerProfile(anyLong(), any(PassengerRequestDTO.class)))
                .thenThrow(new PassengerWithSamePhoneAlreadyExistsException(passengerRequestDTO.phone()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(PassengerExceptionMessageKeys.PASSENGER_WITH_SAME_PHONE_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), new Object[]{passengerRequestDTO.phone()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void deletePassengerProfile_ReturnsPassengerResponseDTO() throws Exception {
        PassengerResponseDTO deletedPassengerResponseDTO = getDeletedPassengerResponseDTO();

        when(passengerService.deletePassengerProfile(anyLong()))
                .thenReturn(deletedPassengerResponseDTO);

        mockMvc.perform(delete(DELETE_PASSENGER_PROFILE_BY_ID, deletedPassengerResponseDTO.id()))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(passengerRequestDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void deletePassengerProfile_ThrowsPassengerNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(passengerService.deletePassengerProfile(anyLong()))
                .thenThrow(new PassengerNotFoundByIdException(NON_EXISTENT_ID));

        mockMvc.perform(withLocale(delete(DELETE_PASSENGER_PROFILE_BY_ID, NON_EXISTENT_ID), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(PassengerExceptionMessageKeys.PASSENGER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{NON_EXISTENT_ID}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void deletePassengerProfile_ThrowsPassengerAlreadyDeletedException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(passengerService.deletePassengerProfile(anyLong()))
                .thenThrow(new PassengerAlreadyDeletedException(passengerResponseDTO.id()));

        mockMvc.perform(withLocale(delete(DELETE_PASSENGER_PROFILE_BY_ID, passengerResponseDTO.id()), currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(PassengerExceptionMessageKeys.PASSENGER_ALREADY_DELETED_MESSAGE_KEY.getMessageKey(), new Object[]{passengerResponseDTO.id()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void addPassenger_ReturnsPassengerResponseDTO() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(ADD_PASSENGER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passengerRequestDTO));

        when(passengerService.addPassenger(any(PassengerRequestDTO.class)))
                .thenReturn(passengerResponseDTO);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(passengerResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void addPassenger_ThrowsPassengerNotFoundByEmailException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(ADD_PASSENGER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passengerRequestDTO));

        when(passengerService.addPassenger(any(PassengerRequestDTO.class)))
                .thenThrow(new PassengerNotFoundByEmailException(passengerRequestDTO.email()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(PassengerExceptionMessageKeys.PASSENGER_NOT_FOUND_BY_EMAIL_MESSAGE_KEY.getMessageKey(), new Object[]{passengerResponseDTO.email()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void addPassenger_ThrowsPassengerWithSameEmailAlreadyExistsException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(ADD_PASSENGER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passengerRequestDTO));

        when(passengerService.addPassenger(any(PassengerRequestDTO.class)))
                .thenThrow(new PassengerWithSameEmailAlreadyExistsException(passengerRequestDTO.email()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(PassengerExceptionMessageKeys.PASSENGER_WITH_SAME_EMAIL_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), new Object[]{passengerResponseDTO.email()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void addPassenger_ThrowsFeignConnectException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(ADD_PASSENGER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passengerRequestDTO));

        when(passengerService.addPassenger(any(PassengerRequestDTO.class)))
                .thenThrow(new FeignConnectException());

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(GeneralExceptionMessageKeys.FEIGN_CONNECT_MESSAGE_KEY.getMessageKey(), new Object[]{}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void getAllPassengers_ReturnsPageOfDriverResponseDTO() throws Exception {
        when(passengerService.getAllPassengers(anyInt(), anyInt(), anyString(), anyBoolean()))
                .thenReturn(getObjectsPage(passengerResponseDTO));

        mockMvc.perform(get(GET_ALL_PASSENGERS)
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
    void getAllPassengers_ThrowsPassengersNotFoundException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(passengerService.getAllPassengers(anyInt(), anyInt(), anyString(), anyBoolean()))
                .thenThrow(new PassengersNotFoundException());

        mockMvc.perform(withLocale(get(GET_ALL_PASSENGERS), currentLocale)
                        .param(REQUEST_PARAM_OFFSET, String.valueOf(TestDataGenerator.getDEFAULT_PAGE()))
                        .param(REQUEST_PARAM_ITEMS, String.valueOf(TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT()))
                        .param(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                        .param(REQUEST_PARAM_DIRECTION, REQUEST_PARAM_DIRECTION_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(PassengerExceptionMessageKeys.PASSENGERS_NOT_FOUND_MESSAGE_KEY.getMessageKey(), new Object[]{}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void changePaymentType_ReturnsPassengerResponseDTO() throws Exception {
        PassengerResponseDTO responseDTO = getPassengerResponseDTOWithChangedPaymentType();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch(UPDATE_PAYMENT_TYPE, responseDTO.id())
                .param(REQUEST_PARAM_PAYMENT_TYPE, PASSENGER_PAYMENT_TYPE_CARD.name());

        when(passengerService.changePaymentType(anyLong(), any(PaymentTypeTypes.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void changePaymentType_ThrowsPassengerNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch(UPDATE_PAYMENT_TYPE, NON_EXISTENT_ID)
                .param(REQUEST_PARAM_PAYMENT_TYPE, PASSENGER_PAYMENT_TYPE_CARD.name());

        when(passengerService.changePaymentType(anyLong(), any(PaymentTypeTypes.class)))
                .thenThrow(new PassengerNotFoundByIdException(NON_EXISTENT_ID));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(PassengerExceptionMessageKeys.PASSENGER_NOT_FOUND_BY_ID_MESSAGE_KEY.getMessageKey(), new Object[]{NON_EXISTENT_ID}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }
}
