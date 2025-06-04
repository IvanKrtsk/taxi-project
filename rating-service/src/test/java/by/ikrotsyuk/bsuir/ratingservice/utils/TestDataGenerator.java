package by.ikrotsyuk.bsuir.ratingservice.utils;

import by.ikrotsyuk.bsuir.communicationparts.event.RatingUpdatedEvent;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingAdminResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.feign.RideFullResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.feign.customtypes.CarClassTypes;
import by.ikrotsyuk.bsuir.ratingservice.dto.feign.customtypes.PaymentTypeTypes;
import by.ikrotsyuk.bsuir.ratingservice.dto.feign.customtypes.RideStatusTypes;
import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import by.ikrotsyuk.bsuir.ratingservice.entity.customtypes.ReviewerTypes;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@UtilityClass
public final class TestDataGenerator {
    private final Long RATING_UPDATED_EVENT_ID = 1L;
    private final Long RATING_UPDATED_EVENT_REVIEWER_ID = 1L;
    private final Long RATING_UPDATED_EVENT_REVIEWED_ID = 1L;
    private final Double RATING_UPDATED_EVENT_RATING = 8.6;

    private final ObjectId REVIEW_ID = ObjectId.get();
    private final Long REVIEW_RIDE_ID = 1L;
    private final Long REVIEW_REVIEWER_ID = 1L;
    private final Long REVIEW_REVIEWED_ID = 1L;
    private final ReviewerTypes REVIEW_REVIEWER_TYPE_PASSENGER = ReviewerTypes.PASSENGER;
    private final ReviewerTypes REVIEW_REVIEWER_TYPE_DRIVER = ReviewerTypes.DRIVER;
    private final Double REVIEW_RATING = 9.3;
    private final String REVIEW_COMMENT = "All was good";
    private final Date REVIEW_CREATED_AT = Date.from(Instant.now());
    private final Date REVIEW_UPDATED_AT = Date.from(Instant.now());
    private final Double CUSTOM_REVIEW_RATING = 8.5;
    private final String CUSTOM_REVIEW_COMMENT = "Nice ride";

    private final Long RIDE_PASSENGER_ID = 1L;
    private final Long RIDE_DRIVER_ID = 1L;
    private final String RIDE_START_LOCATION = "kiseleva 6";
    private final String RIDE_END_LOCATION = "gikalo 9";
    private final BigDecimal RIDE_COST = BigDecimal.TEN;
    private final RideStatusTypes RIDE_RIDE_STATUS = RideStatusTypes.COMPLETED;
    private final PaymentTypeTypes RIDE_PAYMENT_TYPE = PaymentTypeTypes.CASH;
    private final CarClassTypes RIDE_CAR_CLASS = CarClassTypes.COMFORT_PLUS;
    private final OffsetDateTime RIDE_BOOKED_AT = OffsetDateTime.now().minusMinutes(5);
    private final Integer RIDE_ESTIMATED_WAITING_TIME = 4;
    private final OffsetDateTime RIDE_ACCEPTED_AT = OffsetDateTime.now().minusMinutes(4);
    private final OffsetDateTime RIDE_BEGAN_AT = OffsetDateTime.now();
    private final OffsetDateTime RIDE_ENDED_AT = OffsetDateTime.now().plusMinutes(8);
    private final Long CUSTOM_RIDE_PASSENGER_ID = 2L;
    private final Long CUSTOM_RIDE_DRIVER_ID = 2L;

    public static final String REVIEW_INVALID_ID = "ID";
    public static final ObjectId NON_EXISTENT_ID = ObjectId.get();

    public static final String REQUEST_PARAM_OFFSET = "offset";
    public static final String REQUEST_PARAM_ITEMS = "itemCount";
    public static final String REQUEST_PARAM_FIELD = "field";
    public static final String REQUEST_PARAM_DIRECTION = "isSortDirectionAsc";
    public static final String REQUEST_PARAM_DIRECTION_VALUE = "true";
    public static final String REQUEST_PARAM_REVIEWER_TYPE = "reviewerType";
    public static final String RESPONSE_FIELD_RATING = "rating";
    public static final String RESPONSE_FIELD_COMMENT = "comment";
    public static final String RESPONSE_PAGE_ELEMENTS_COUNT_FIELD = "page.totalElements";
    public static final int RESPONSE_PAGE_ELEMENTS_COUNT = 1;
    public static final Double RESPONSE_FIELD_RATING_VALUE = REVIEW_RATING;
    public static final String RESPONSE_FIELD_COMMENT_VALUE = REVIEW_COMMENT;

    public static final String REVIEWS_COLLECTION_NAME = "reviews";

    public static final String GET_RATING_BY_ID = "/api/v1/ratings/rating/{ratingId}";
    public static final String GET_ALL_RATINGS = "/api/v1/ratings";
    public static final String UPDATE_RATING_BY_ID = "/api/v1/ratings/{ratingId}";
    public static final String DELETE_RATING_BY_ID = "/api/v1/ratings/{ratingId}";
    public static final String LEAVE_REVIEW = "/api/v1/reviewer/ratings/";
    public static final String GET_LEAVED_REVIEWS = "/api/v1/reviewer/ratings/{reviewerId}";
    public static final String GET_REVIEW_BY_ID = "/api/v1/reviewer/ratings/rating/{ratingId}";

    @Getter
    private static final int DEFAULT_PAGE = 0;
    @Getter
    private static final int DEFAULT_ITEMS_PER_PAGE_COUNT = 10;
    @Getter
    private static final String DEFAULT_SORT_FIELD = "id";
    @Getter
    private static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

    public static final String baseUri = "http://localhost:";

    public static RatingEntity getRatingEntity() {
        return RatingEntity.builder()
                .id(REVIEW_ID)
                .rideId(REVIEW_RIDE_ID)
                .reviewerId(REVIEW_REVIEWER_ID)
                .reviewedId(REVIEW_REVIEWED_ID)
                .reviewerType(REVIEW_REVIEWER_TYPE_PASSENGER)
                .rating(REVIEW_RATING)
                .comment(REVIEW_COMMENT)
                .createdAt(REVIEW_CREATED_AT)
                .updatedAt(REVIEW_UPDATED_AT)
                .build();
    }

    public static RatingAdminResponseDTO getRatingAdminResponseDTO() {
        return new RatingAdminResponseDTO(
                REVIEW_ID,
                REVIEW_RIDE_ID,
                REVIEW_REVIEWER_ID,
                REVIEW_REVIEWED_ID,
                REVIEW_REVIEWER_TYPE_PASSENGER,
                REVIEW_RATING,
                REVIEW_COMMENT,
                REVIEW_CREATED_AT,
                REVIEW_UPDATED_AT
        );
    }

    public static RatingRequestDTO getRatingRequestDTO() {
        return new RatingRequestDTO(
                REVIEW_RIDE_ID,
                REVIEW_REVIEWER_ID,
                REVIEW_REVIEWED_ID,
                REVIEW_REVIEWER_TYPE_PASSENGER,
                REVIEW_RATING,
                REVIEW_COMMENT
        );
    }

    public static RatingRequestDTO getCustomRatingRequestDTO() {
        return new RatingRequestDTO(
                REVIEW_RIDE_ID,
                REVIEW_REVIEWER_ID,
                REVIEW_REVIEWED_ID,
                REVIEW_REVIEWER_TYPE_PASSENGER,
                CUSTOM_REVIEW_RATING,
                CUSTOM_REVIEW_COMMENT
        );
    }

    public static RatingResponseDTO getRatingResponseDTO() {
        return new RatingResponseDTO(
                REVIEW_ID,
                REVIEW_RIDE_ID,
                REVIEW_REVIEWER_ID,
                REVIEW_REVIEWED_ID,
                REVIEW_REVIEWER_TYPE_PASSENGER,
                REVIEW_RATING,
                REVIEW_COMMENT
        );
    }

    public static RideFullResponseDTO getRideFullResponseDTO() {
        return new RideFullResponseDTO(
                REVIEW_RIDE_ID,
                RIDE_PASSENGER_ID,
                RIDE_DRIVER_ID,
                RIDE_START_LOCATION,
                RIDE_END_LOCATION,
                RIDE_COST,
                RIDE_RIDE_STATUS,
                RIDE_PAYMENT_TYPE,
                RIDE_CAR_CLASS,
                RIDE_BOOKED_AT,
                RIDE_ESTIMATED_WAITING_TIME,
                RIDE_ACCEPTED_AT,
                RIDE_BEGAN_AT,
                RIDE_ENDED_AT
        );
    }

    public static RideFullResponseDTO getRideFullResponseDTOWithNullDriverId() {
        return new RideFullResponseDTO(
                REVIEW_RIDE_ID,
                RIDE_PASSENGER_ID,
                null,
                RIDE_START_LOCATION,
                RIDE_END_LOCATION,
                RIDE_COST,
                RIDE_RIDE_STATUS,
                RIDE_PAYMENT_TYPE,
                RIDE_CAR_CLASS,
                RIDE_BOOKED_AT,
                RIDE_ESTIMATED_WAITING_TIME,
                RIDE_ACCEPTED_AT,
                RIDE_BEGAN_AT,
                RIDE_ENDED_AT
        );
    }

    public static RideFullResponseDTO getRideFullResponseDTOWithCustomPassengerId() {
        return new RideFullResponseDTO(
                REVIEW_RIDE_ID,
                CUSTOM_RIDE_PASSENGER_ID,
                RIDE_DRIVER_ID,
                RIDE_START_LOCATION,
                RIDE_END_LOCATION,
                RIDE_COST,
                RIDE_RIDE_STATUS,
                RIDE_PAYMENT_TYPE,
                RIDE_CAR_CLASS,
                RIDE_BOOKED_AT,
                RIDE_ESTIMATED_WAITING_TIME,
                RIDE_ACCEPTED_AT,
                RIDE_BEGAN_AT,
                RIDE_ENDED_AT
        );
    }

    public static RideFullResponseDTO getRideFullResponseDTOWithCustomDriverId() {
        return new RideFullResponseDTO(
                REVIEW_RIDE_ID,
                RIDE_PASSENGER_ID,
                CUSTOM_RIDE_DRIVER_ID,
                RIDE_START_LOCATION,
                RIDE_END_LOCATION,
                RIDE_COST,
                RIDE_RIDE_STATUS,
                RIDE_PAYMENT_TYPE,
                RIDE_CAR_CLASS,
                RIDE_BOOKED_AT,
                RIDE_ESTIMATED_WAITING_TIME,
                RIDE_ACCEPTED_AT,
                RIDE_BEGAN_AT,
                RIDE_ENDED_AT
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
