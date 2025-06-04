package by.ikrotsyuk.bsuir.ratingservice.it;

import by.ikrotsyuk.bsuir.ratingservice.config.KafkaConfig;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingAdminResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import by.ikrotsyuk.bsuir.ratingservice.kafka.producer.RatingProducer;
import by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.DELETE_RATING_BY_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.GET_ALL_RATINGS;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.GET_RATING_BY_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.NON_EXISTENT_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_DIRECTION;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_FIELD;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_ITEMS;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_OFFSET;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.RESPONSE_FIELD_COMMENT;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.RESPONSE_FIELD_RATING;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.RESPONSE_PAGE_ELEMENTS_COUNT;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.RESPONSE_PAGE_ELEMENTS_COUNT_FIELD;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REVIEWS_COLLECTION_NAME;

import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REVIEW_INVALID_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.UPDATE_RATING_BY_ID;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockitoBean(types = {RatingProducer.class, KafkaConfig.class})
public class RatingControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:5.0.2"));

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MongoTemplate mongoTemplate;

    private final RatingAdminResponseDTO ratingAdminResponseDTO = TestDataGenerator.getRatingAdminResponseDTO();
    private final RatingEntity ratingEntity = TestDataGenerator.getRatingEntity();
    private final RatingRequestDTO customRatingRequestDTO = TestDataGenerator.getCustomRatingRequestDTO();

    private final String NEEDS_CLEAR_COLLECTION_TAG = "NEEDS_CLEAR_COLLECTION";

    @BeforeEach
    void setup(TestInfo testInfo) {
        RestAssured.port = this.port;
        mongoTemplate.dropCollection(REVIEWS_COLLECTION_NAME);
        var tags = testInfo.getTags();

        if(!tags.contains(NEEDS_CLEAR_COLLECTION_TAG)) {
            mongoTemplate.insert(ratingEntity);
        }
    }

    @Test
    void getRatingById_ReturnsRatingAdminResponseDTO() throws JsonProcessingException {
        given()
                .when()
                .get(GET_RATING_BY_ID, ratingAdminResponseDTO.id().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(ratingAdminResponseDTO)));
    }

    @Test
    void getRatingById_ThrowsIdIsNotValidException() {
        given()
                .when()
                .get(GET_RATING_BY_ID, REVIEW_INVALID_ID)
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void getRatingById_ThrowsReviewNotFoundByIdException() {
        given()
                .when()
                .get(GET_RATING_BY_ID, NON_EXISTENT_ID.toString())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void getAllReviews_ReturnsPageOfRatingAdminResponseDTO() {
        given()
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_ALL_RATINGS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(RESPONSE_PAGE_ELEMENTS_COUNT_FIELD, equalTo(RESPONSE_PAGE_ELEMENTS_COUNT));
    }

    @Test
    @Tag(NEEDS_CLEAR_COLLECTION_TAG)
    void getAllReviews_ThrowsReviewsNotFoundException() {
        given()
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_ALL_RATINGS)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void editReview_ReturnsRatingAdminResponseDTO() {
        given()
                .contentType(ContentType.JSON)
                .body(customRatingRequestDTO)
                .when()
                .put(UPDATE_RATING_BY_ID, ratingAdminResponseDTO.id().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(RESPONSE_FIELD_RATING, equalTo(Float.valueOf(String.valueOf(customRatingRequestDTO.rating()))))
                .body(RESPONSE_FIELD_COMMENT, equalTo(customRatingRequestDTO.comment()));
    }

    @Test
    void editReview_ThrowsIdIsNotValidException() {
        given()
                .contentType(ContentType.JSON)
                .body(customRatingRequestDTO)
                .when()
                .put(UPDATE_RATING_BY_ID, REVIEW_INVALID_ID)
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void editReview_ThrowsReviewNotFoundByIdException() {
        given()
                .contentType(ContentType.JSON)
                .body(customRatingRequestDTO)
                .when()
                .put(UPDATE_RATING_BY_ID, NON_EXISTENT_ID.toString())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deleteReview_returnsRatingAdminResponseDTO() {
        given()
                .when()
                .delete(DELETE_RATING_BY_ID, ratingAdminResponseDTO.id().toString())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deleteReview_ThrowsIdIsNotValidException() {
        given()
                .when()
                .delete(DELETE_RATING_BY_ID, REVIEW_INVALID_ID)
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void deleteReview_ThrowsReviewNotFoundByIdException() {
        given()
                .when()
                .delete(DELETE_RATING_BY_ID, NON_EXISTENT_ID.toString())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
