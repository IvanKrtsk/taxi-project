package by.ikrotsyuk.bsuir.ratingservice.controller.impl;

import by.ikrotsyuk.bsuir.ratingservice.config.TestConfig;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.IdIsNotValidException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.ReviewAlreadyExistsException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.ReviewNotFoundByIdException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.ReviewsNotFoundException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.RideNotAcceptedException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.RideNotBelongToDriverException;
import by.ikrotsyuk.bsuir.ratingservice.exception.exceptions.RideNotBelongToPassengerException;
import by.ikrotsyuk.bsuir.ratingservice.exception.keys.GeneralExceptionMessageKeys;
import by.ikrotsyuk.bsuir.ratingservice.service.impl.RatingReviewerServiceImpl;
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
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.GET_LEAVED_REVIEWS;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.GET_REVIEW_BY_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.LEAVE_REVIEW;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.NON_EXISTENT_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_DIRECTION;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_DIRECTION_VALUE;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_FIELD;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_ITEMS;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_OFFSET;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_REVIEWER_TYPE;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REVIEW_INVALID_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.UPDATE_RATING_BY_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RatingReviewerController.class)
@Import(TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RatingReviewerControllerTest {
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
    private RatingReviewerServiceImpl ratingReviewerServiceImpl;

    private final RatingRequestDTO ratingRequestDTO = TestDataGenerator.getRatingRequestDTO();
    private final RatingResponseDTO ratingResponseDTO = TestDataGenerator.getRatingResponseDTO();
    private final Page<RatingResponseDTO> ratingResponseDTOPage = TestDataGenerator.getObjectsPage(ratingResponseDTO);

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
    void leaveReview_ReturnsRatingResponseDTO() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(LEAVE_REVIEW)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ratingRequestDTO));

        when(ratingReviewerServiceImpl.leaveReview(any(RatingRequestDTO.class)))
                .thenReturn(ratingResponseDTO);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ratingResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void leaveReview_ThrowsReviewAlreadyExistsException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(LEAVE_REVIEW)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ratingRequestDTO));

        when(ratingReviewerServiceImpl.leaveReview(any(RatingRequestDTO.class)))
                .thenThrow(new ReviewAlreadyExistsException(ratingRequestDTO.rideId(), ratingRequestDTO.reviewerType()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(GeneralExceptionMessageKeys.REVIEW_FOR_RIDE_BY_REVIEWER_ALREADY_EXISTS_MESSAGE_KEY.getMessageKey(), new Object[]{ratingRequestDTO.rideId(), ratingRequestDTO.reviewerType()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void leaveReview_ThrowsRideNotAcceptedException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(LEAVE_REVIEW)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ratingRequestDTO));

        when(ratingReviewerServiceImpl.leaveReview(any(RatingRequestDTO.class)))
                .thenThrow(new RideNotAcceptedException(ratingRequestDTO.rideId()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(GeneralExceptionMessageKeys.RIDE_NOT_ACCEPTED_MESSAGE_KEY.getMessageKey(), new Object[]{ratingRequestDTO.rideId(), ratingRequestDTO.reviewerType()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void leaveReview_ThrowsRideNotBelongToPassengerException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(LEAVE_REVIEW)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ratingRequestDTO));

        when(ratingReviewerServiceImpl.leaveReview(any(RatingRequestDTO.class)))
                .thenThrow(new RideNotBelongToPassengerException(ratingRequestDTO.rideId(), ratingRequestDTO.reviewerId()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(GeneralExceptionMessageKeys.RIDE_NOT_BELONG_TO_PASSENGER_MESSAGE_KEY.getMessageKey(), new Object[]{ratingRequestDTO.rideId(), ratingRequestDTO.reviewerId()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void leaveReview_ThrowsRideNotBelongToDriverException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(LEAVE_REVIEW)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ratingRequestDTO));

        when(ratingReviewerServiceImpl.leaveReview(any(RatingRequestDTO.class)))
                .thenThrow(new RideNotBelongToDriverException(ratingRequestDTO.rideId(), ratingRequestDTO.reviewedId()));

        mockMvc.perform(withLocale(requestBuilder, currentLocale))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(GeneralExceptionMessageKeys.RIDE_NOT_BELONG_TO_DRIVER_MESSAGE_KEY.getMessageKey(), new Object[]{ratingRequestDTO.rideId(), ratingRequestDTO.reviewedId()}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @Test
    void viewLeavedReviews_ReturnsPageOfRatingResponseDTO() throws Exception {
        when(ratingReviewerServiceImpl.viewLeavedReviews(anyLong(), any(ReviewerTypes.class), anyInt(), anyInt(), anyString(), anyBoolean()))
                .thenReturn(ratingResponseDTOPage);

        mockMvc.perform(get(GET_LEAVED_REVIEWS, ratingRequestDTO.reviewerId())
                        .param(REQUEST_PARAM_REVIEWER_TYPE, String.valueOf(ratingRequestDTO.reviewerType()))
                        .param(REQUEST_PARAM_OFFSET, String.valueOf(TestDataGenerator.getDEFAULT_PAGE()))
                        .param(REQUEST_PARAM_ITEMS, String.valueOf(TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT()))
                        .param(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                        .param(REQUEST_PARAM_DIRECTION, REQUEST_PARAM_DIRECTION_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ratingResponseDTOPage)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void viewLeavedReviews_ThrowsReviewsNotFoundException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(ratingReviewerServiceImpl.viewLeavedReviews(anyLong(), any(ReviewerTypes.class), anyInt(), anyInt(), anyString(), anyBoolean()))
                .thenThrow(new ReviewsNotFoundException());

        mockMvc.perform(withLocale(get(GET_LEAVED_REVIEWS, ratingRequestDTO.reviewerId()), currentLocale)
                        .param(REQUEST_PARAM_REVIEWER_TYPE, String.valueOf(ratingRequestDTO.reviewerType()))
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
    void getReviewById_ReturnsRatingResponseDTO() throws Exception {
        when(ratingReviewerServiceImpl.getReviewById(anyString()))
                .thenReturn(ratingResponseDTO);

        mockMvc.perform(get(GET_REVIEW_BY_ID, ratingResponseDTO.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(ratingResponseDTO)));
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getReviewById_ThrowsIdIsNotValidException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(ratingReviewerServiceImpl.getReviewById(anyString()))
                .thenThrow(new IdIsNotValidException(REVIEW_INVALID_ID));

        mockMvc.perform(withLocale(get(GET_REVIEW_BY_ID, ratingResponseDTO.id()), currentLocale))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(GeneralExceptionMessageKeys.ID_IS_NOT_VALID_MESSAGE_KEY.getMessageKey(), new Object[]{REVIEW_INVALID_ID}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }

    @ParameterizedTest
    @MethodSource("supportedLocales")
    void getReviewById_ThrowsReviewNotFoundByIdException(String locale) throws Exception {
        Locale currentLocale = Locale.forLanguageTag(locale);

        when(ratingReviewerServiceImpl.getReviewById(anyString()))
                .thenThrow(new ReviewNotFoundByIdException(NON_EXISTENT_ID.toString()));

        mockMvc.perform(withLocale(get(GET_REVIEW_BY_ID, ratingResponseDTO.id()), currentLocale))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(messageSource.getMessage(GeneralExceptionMessageKeys.ID_IS_NOT_VALID_MESSAGE_KEY.getMessageKey(), new Object[]{NON_EXISTENT_ID}, currentLocale)))
                .andExpect(jsonPath("$.offsetDateTime").exists());
    }
}
