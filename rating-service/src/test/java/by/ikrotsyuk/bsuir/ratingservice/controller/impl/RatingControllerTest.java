package by.ikrotsyuk.bsuir.ratingservice.controller.impl;

import by.ikrotsyuk.bsuir.ratingservice.config.TestConfig;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingAdminResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.IdIsNotValidException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.ReviewNotFoundByIdException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.ReviewsNotFoundException;
import by.ikrotsyuk.bsuir.ratingservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.service.impl.RatingAdminServiceImpl;
import by.ikrotsyuk.bsuir.ratingservice.utils.LocaleUtils;
import by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.DELETE_RATING_BY_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.GET_ALL_RATINGS;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.GET_RATING_BY_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.NON_EXISTENT_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_DIRECTION;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_DIRECTION_VALUE;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_FIELD;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_ITEMS;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_OFFSET;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REVIEW_INVALID_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.UPDATE_RATING_BY_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.getObjectsPage;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.getRatingAdminResponseDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RatingController.class)
@Import(TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    @Qualifier("webApplicationContext")
    private ResourcePatternResolver resourcePatternResolver;

    @MockitoBean
    private RatingAdminServiceImpl ratingAdminServiceImpl;

    private final RatingRequestDTO ratingRequestDTO = TestDataGenerator.getRatingRequestDTO();
    private final RatingAdminResponseDTO ratingAdminResponseDTO = TestDataGenerator.getRatingAdminResponseDTO();

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
    void getRatingById_ReturnsRatingAdminResponseDTO() throws Exception {
        when(ratingAdminServiceImpl.getReviewById(anyString()))
                .thenReturn(ratingAdminResponseDTO);

        mockMvc.perform(get(GET_RATING_BY_ID, ratingAdminResponseDTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ratingAdminResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getRatingById_ThrowsIdIsNotValidException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(ratingAdminServiceImpl.getReviewById(anyString()))
                .thenThrow(new IdIsNotValidException(REVIEW_INVALID_ID));

        mockMvc.perform(withLocale(get(GET_RATING_BY_ID, ratingAdminResponseDTO.id()), currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(GeneralExceptionMessageKeys.ID_IS_NOT_VALID_MESSAGE_KEY.getMessageKey(), new Object[]{REVIEW_INVALID_ID}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getRatingById_ReviewNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(ratingAdminServiceImpl.getReviewById(anyString()))
                .thenThrow(new ReviewNotFoundByIdException(NON_EXISTENT_ID.toString()));

        mockMvc.perform(withLocale(get(GET_RATING_BY_ID, NON_EXISTENT_ID.toString()), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(GeneralExceptionMessageKeys.ID_IS_NOT_VALID_MESSAGE_KEY.getMessageKey(), new Object[]{NON_EXISTENT_ID}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void getAllRatings_ReturnsPageOfRatingAdminResponseDTO() throws Exception {
        when(ratingAdminServiceImpl.getAllReviews(anyInt(), anyInt(), anyString(), anyBoolean()))
                .thenReturn(getObjectsPage(getRatingAdminResponseDTO()));

        mockMvc.perform(get(GET_ALL_RATINGS)
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
    void getRatingById_ThrowsReviewsNotFoundException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(ratingAdminServiceImpl.getAllReviews(anyInt(), anyInt(), anyString(), anyBoolean()))
                .thenThrow(new ReviewsNotFoundException());

        mockMvc.perform(withLocale(get(GET_ALL_RATINGS), currentLocale)
                        .param(REQUEST_PARAM_OFFSET, String.valueOf(TestDataGenerator.getDEFAULT_PAGE()))
                        .param(REQUEST_PARAM_ITEMS, String.valueOf(TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT()))
                        .param(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                        .param(REQUEST_PARAM_DIRECTION, REQUEST_PARAM_DIRECTION_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(GeneralExceptionMessageKeys.REVIEWS_NOT_FOUND_MESSAGE_KEY.getMessageKey(), new Object[]{}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void editReview_ReturnsRatingAdminResponseDTO() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(UPDATE_RATING_BY_ID, ratingAdminResponseDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ratingRequestDTO));

        when(ratingAdminServiceImpl.editReview(anyString(), any(RatingRequestDTO.class)))
                .thenReturn(ratingAdminResponseDTO);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ratingAdminResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void editReview_ThrowsIdIsNotValidException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(UPDATE_RATING_BY_ID, REVIEW_INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ratingRequestDTO));

        when(ratingAdminServiceImpl.editReview(anyString(), any(RatingRequestDTO.class)))
                .thenThrow(new IdIsNotValidException(REVIEW_INVALID_ID));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(GeneralExceptionMessageKeys.ID_IS_NOT_VALID_MESSAGE_KEY.getMessageKey(), new Object[]{REVIEW_INVALID_ID}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void editReview_ThrowsReviewNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(UPDATE_RATING_BY_ID, NON_EXISTENT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ratingRequestDTO));

        when(ratingAdminServiceImpl.editReview(anyString(), any(RatingRequestDTO.class)))
                .thenThrow(new ReviewNotFoundByIdException(NON_EXISTENT_ID.toString()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(GeneralExceptionMessageKeys.ID_IS_NOT_VALID_MESSAGE_KEY.getMessageKey(), new Object[]{NON_EXISTENT_ID}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void deleteReview_ReturnsRatingAdminResponseDTO() throws Exception {
        when(ratingAdminServiceImpl.deleteReview(anyString()))
                .thenReturn(ratingAdminResponseDTO);

        mockMvc.perform(delete(DELETE_RATING_BY_ID, ratingAdminResponseDTO.id()))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ratingAdminResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void deleteReview_ThrowsIdIsNotValidException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(ratingAdminServiceImpl.deleteReview(anyString()))
                .thenThrow(new IdIsNotValidException(REVIEW_INVALID_ID));

        mockMvc.perform(withLocale(delete(DELETE_RATING_BY_ID, ratingAdminResponseDTO.id()), currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(GeneralExceptionMessageKeys.ID_IS_NOT_VALID_MESSAGE_KEY.getMessageKey(), new Object[]{REVIEW_INVALID_ID}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void deleteReview_ThrowsReviewNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(ratingAdminServiceImpl.deleteReview(anyString()))
                .thenThrow(new ReviewNotFoundByIdException(NON_EXISTENT_ID.toString()));

        mockMvc.perform(withLocale(delete(DELETE_RATING_BY_ID, ratingAdminResponseDTO.id()), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(GeneralExceptionMessageKeys.ID_IS_NOT_VALID_MESSAGE_KEY.getMessageKey(), new Object[]{NON_EXISTENT_ID}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }
}
