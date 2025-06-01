package by.ikrotsyuk.bsuir.passengerservice.utils;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;

import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.passengerservice.entity.PassengerEntity;
import by.ikrotsyuk.bsuir.passengerservice.entity.customtypes.PaymentTypeTypes;
import by.ikrotsyuk.bsuir.passengerservice.entity.customtypes.StatusTypes;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public final class TestDataGenerator {
    private final Long RATING_UPDATED_EVENT_ID = 1L;
    private final Long RATING_UPDATED_EVENT_REVIEWER_ID = 1L;
    private final Long RATING_UPDATED_EVENT_REVIEWED_ID = 1L;
    private final Double RATING_UPDATED_EVENT_RATING = 8.6;

    private final Long PASSENGER_ID = 1L;
    private final String PASSENGER_NAME = "Ivan";
    private final String PASSENGER_EMAIL = "ivan@gmail.com";
    private final String PASSENGER_PHONE = "+375296785412";
    private final Double PASSENGER_RATING = 9.0;
    private final Long PASSENGER_RIDES_COUNT = 10L;
    private final Boolean PASSENGER_IS_DELETED = false;
    private final StatusTypes PASSENGER_STATUS = StatusTypes.AVAILABLE;
    private final PaymentTypeTypes PASSENGER_PAYMENT_TYPE = PaymentTypeTypes.CASH;
    private final OffsetDateTime PASSENGER_CREATED_AT = OffsetDateTime.now();
    private final OffsetDateTime PASSENGER_UPDATED_AT = OffsetDateTime.now();

    private final Double PASSENGER_ZERO_RATING = 0.0;
    private final Long PASSENGER_ZERO_RIDES_COUNT = 0L;
    @Getter
    private final String PASSENGER_CUSTOM_NAME = "Oleg";
    @Getter
    private final String PASSENGER_CUSTOM_EMAIL = "oleg@gmail.com";
    @Getter
    private final String PASSENGER_CUSTOM_PHONE = "+375294445690";
    private final Boolean PASSENGER_IS_DELETED_TRUE = true;

    public static final Long NON_EXISTENT_ID = 10L;
    public static final PaymentTypeTypes PASSENGER_PAYMENT_TYPE_CARD = PaymentTypeTypes.CARD;

    public static final String REQUEST_PARAM_OFFSET = "offset";
    public static final String REQUEST_PARAM_ITEMS = "itemCount";
    public static final String REQUEST_PARAM_FIELD = "field";
    public static final String REQUEST_PARAM_DIRECTION = "isSortDirectionAsc";
    public static final String REQUEST_PARAM_DIRECTION_VALUE = "true";
    public static final String REQUEST_PARAM_PAYMENT_TYPE = "paymentType";

    public static final String SQL_DELETE_PASSENGERS_TABLE = "DELETE FROM passengers";
    public static final String SQL_RESTART_PASSENGERS_SEQUENCE = "ALTER SEQUENCE passengers_id_seq RESTART WITH 1";

    @Getter
    private static final int DEFAULT_PAGE = 0;
    @Getter
    private static final int DEFAULT_ITEMS_PER_PAGE_COUNT = 10;
    @Getter
    private static final String DEFAULT_SORT_FIELD = "id";
    @Getter
    private static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

    public static final String GET_PASSENGER_PROFILE_BY_ID = "/api/v1/passengers/{passengerId}/profile";
    public static final String GET_PASSENGER_RATING_BY_ID = "/api/v1/passengers/{passengerId}/rating";
    public static final String UPDATE_PASSENGER_PROFILE_BY_ID = "/api/v1/passengers/{passengerId}";
    public static final String DELETE_PASSENGER_PROFILE_BY_ID = "/api/v1/passengers/{passengerId}";
    public static final String ADD_PASSENGER = "/api/v1/passengers";
    public static final String GET_ALL_PASSENGERS = "/api/v1/passengers";
    public static final String UPDATE_PAYMENT_TYPE = "/api/v1/passengers/{passengerId}";

    public static final String baseUri = "http://localhost:";

    public static PassengerEntity getPassengerEntity() {
        return PassengerEntity.builder()
                .id(PASSENGER_ID)
                .name(PASSENGER_NAME)
                .email(PASSENGER_EMAIL)
                .phone(PASSENGER_PHONE)
                .rating(PASSENGER_RATING)
                .totalRides(PASSENGER_RIDES_COUNT)
                .isDeleted(PASSENGER_IS_DELETED)
                .status(PASSENGER_STATUS)
                .paymentType(PASSENGER_PAYMENT_TYPE)
                .createdAt(PASSENGER_CREATED_AT)
                .updatedAt(PASSENGER_UPDATED_AT)
                .build();

    }

    public static PassengerEntity getCustomPassengerEntity() {
        return PassengerEntity.builder()
                .name(PASSENGER_NAME)
                .email(PASSENGER_CUSTOM_EMAIL)
                .phone(PASSENGER_CUSTOM_PHONE)
                .rating(PASSENGER_RATING)
                .totalRides(PASSENGER_RIDES_COUNT)
                .isDeleted(PASSENGER_IS_DELETED)
                .status(PASSENGER_STATUS)
                .paymentType(PASSENGER_PAYMENT_TYPE)
                .createdAt(PASSENGER_CREATED_AT)
                .updatedAt(PASSENGER_UPDATED_AT)
                .build();

    }

    public static PassengerEntity getIntegrationPassengerEntity() {
        return PassengerEntity.builder()
                .name(PASSENGER_NAME)
                .email(PASSENGER_EMAIL)
                .phone(PASSENGER_PHONE)
                .rating(PASSENGER_RATING)
                .totalRides(PASSENGER_RIDES_COUNT)
                .isDeleted(PASSENGER_IS_DELETED)
                .status(PASSENGER_STATUS)
                .paymentType(PASSENGER_PAYMENT_TYPE)
                .createdAt(PASSENGER_CREATED_AT)
                .updatedAt(PASSENGER_UPDATED_AT)
                .build();
    }

    public static PassengerEntity getCleanPassengerEntity() {
        return PassengerEntity.builder()
                .id(PASSENGER_ID)
                .name(PASSENGER_NAME)
                .email(PASSENGER_EMAIL)
                .phone(PASSENGER_PHONE)
                .rating(PASSENGER_ZERO_RATING)
                .totalRides(PASSENGER_ZERO_RIDES_COUNT)
                .isDeleted(PASSENGER_IS_DELETED)
                .status(PASSENGER_STATUS)
                .paymentType(PASSENGER_PAYMENT_TYPE)
                .createdAt(PASSENGER_CREATED_AT)
                .updatedAt(PASSENGER_UPDATED_AT)
                .build();

    }

    public static PassengerEntity getDeletedPassengerEntity() {
        return PassengerEntity.builder()
                .id(PASSENGER_ID)
                .name(PASSENGER_NAME)
                .email(PASSENGER_EMAIL)
                .phone(PASSENGER_PHONE)
                .rating(PASSENGER_RATING)
                .totalRides(PASSENGER_RIDES_COUNT)
                .isDeleted(PASSENGER_IS_DELETED_TRUE)
                .status(PASSENGER_STATUS)
                .paymentType(PASSENGER_PAYMENT_TYPE)
                .createdAt(PASSENGER_CREATED_AT)
                .updatedAt(PASSENGER_UPDATED_AT)
                .build();

    }

    public static PassengerRequestDTO getPassengerRequestDTO() {
        return new PassengerRequestDTO(
                PASSENGER_NAME,
                PASSENGER_EMAIL,
                PASSENGER_PHONE
        );
    }

    public static PassengerRequestDTO getCustomPassengerRequestDTO() {
        return new PassengerRequestDTO(
                PASSENGER_CUSTOM_NAME,
                PASSENGER_CUSTOM_EMAIL,
                PASSENGER_CUSTOM_PHONE
        );
    }

    public static PassengerResponseDTO getPassengerResponseDTO() {
        return new PassengerResponseDTO(
                PASSENGER_ID,
                PASSENGER_NAME,
                PASSENGER_EMAIL,
                PASSENGER_PHONE,
                PASSENGER_RATING,
                PASSENGER_RIDES_COUNT,
                PASSENGER_IS_DELETED,
                PASSENGER_PAYMENT_TYPE
        );
    }

    public static PassengerResponseDTO getPassengerResponseDTOWithChangedPaymentType() {
        return new PassengerResponseDTO(
                PASSENGER_ID,
                PASSENGER_NAME,
                PASSENGER_EMAIL,
                PASSENGER_PHONE,
                PASSENGER_RATING,
                PASSENGER_RIDES_COUNT,
                PASSENGER_IS_DELETED,
                PASSENGER_PAYMENT_TYPE_CARD
        );
    }

    public static PassengerResponseDTO getCleanPassengerResponseDTO() {
        return new PassengerResponseDTO(
                PASSENGER_ID,
                PASSENGER_NAME,
                PASSENGER_EMAIL,
                PASSENGER_PHONE,
                PASSENGER_ZERO_RATING,
                PASSENGER_ZERO_RIDES_COUNT,
                PASSENGER_IS_DELETED,
                PASSENGER_PAYMENT_TYPE
        );
    }

    public static PassengerResponseDTO getDeletedPassengerResponseDTO() {
        return new PassengerResponseDTO(
                PASSENGER_ID,
                PASSENGER_NAME,
                PASSENGER_EMAIL,
                PASSENGER_PHONE,
                PASSENGER_RATING,
                PASSENGER_RIDES_COUNT,
                PASSENGER_IS_DELETED_TRUE,
                PASSENGER_PAYMENT_TYPE
        );
    }

    public static PassengerResponseDTO getCustomPassengerResponseDTO() {
        return new PassengerResponseDTO(
                PASSENGER_ID,
                PASSENGER_CUSTOM_NAME,
                PASSENGER_CUSTOM_EMAIL,
                PASSENGER_CUSTOM_PHONE,
                PASSENGER_RATING,
                PASSENGER_RIDES_COUNT,
                PASSENGER_IS_DELETED,
                PASSENGER_PAYMENT_TYPE
        );
    }

    public static RatingUpdatedEvent getRatingUpdatedEvent() {
        return new RatingUpdatedEvent(
                RATING_UPDATED_EVENT_ID,
                RATING_UPDATED_EVENT_REVIEWER_ID,
                RATING_UPDATED_EVENT_REVIEWED_ID,
                RATING_UPDATED_EVENT_RATING
        );
    }

    @SafeVarargs
    public static <T> Page<T> getObjectsPage(T... entitiesArr) {
        PageRequest pageable = PageRequest.of(DEFAULT_PAGE, entitiesArr.length, Sort.by(DEFAULT_SORT_FIELD));

        List<T> entities = Arrays.stream(entitiesArr)
                .toList();

        return new PageImpl<>(entities, pageable, entities.size());
    }

    public static Pageable getPageRequest() {
        return PageRequest.of(DEFAULT_PAGE, DEFAULT_ITEMS_PER_PAGE_COUNT, Sort.by(DEFAULT_SORT_DIRECTION, DEFAULT_SORT_FIELD));
    }
}
