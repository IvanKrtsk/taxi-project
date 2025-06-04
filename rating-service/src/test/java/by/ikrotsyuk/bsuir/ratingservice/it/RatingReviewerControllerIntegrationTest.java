package by.ikrotsyuk.bsuir.ratingservice.it;

import by.ikrotsyuk.bsuir.ratingservice.config.KafkaConfig;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingRequestDTO;
import by.ikrotsyuk.bsuir.ratingservice.dto.RatingResponseDTO;
import by.ikrotsyuk.bsuir.ratingservice.entity.RatingEntity;
import by.ikrotsyuk.bsuir.ratingservice.kafka.producer.RatingProducer;
import by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
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

import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.GET_LEAVED_REVIEWS;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.GET_REVIEW_BY_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.LEAVE_REVIEW;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.NON_EXISTENT_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_DIRECTION;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_FIELD;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_ITEMS;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_OFFSET;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REQUEST_PARAM_REVIEWER_TYPE;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.RESPONSE_FIELD_COMMENT;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.RESPONSE_FIELD_COMMENT_VALUE;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.RESPONSE_FIELD_RATING;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.RESPONSE_FIELD_RATING_VALUE;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.RESPONSE_PAGE_ELEMENTS_COUNT;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.RESPONSE_PAGE_ELEMENTS_COUNT_FIELD;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REVIEWS_COLLECTION_NAME;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.REVIEW_INVALID_ID;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.getRideFullResponseDTO;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.getRideFullResponseDTOWithCustomDriverId;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.getRideFullResponseDTOWithCustomPassengerId;
import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.getRideFullResponseDTOWithNullDriverId;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockitoBean(types = {RatingProducer.class, KafkaConfig.class})
public class RatingReviewerControllerIntegrationTest {
    @LocalServerPort
    private int port;

    private WireMockServer wireMockServer;

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:5.0.2"));

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MongoTemplate mongoTemplate;

    private final RatingResponseDTO ratingResponseDTO = TestDataGenerator.getRatingResponseDTO();
    private final RatingEntity ratingEntity = TestDataGenerator.getRatingEntity();
    private final RatingRequestDTO ratingRequestDTO = TestDataGenerator.getRatingRequestDTO();

    private final String NEEDS_CLEAR_COLLECTION_TAG = "NEEDS_CLEAR_COLLECTION";
    private final String NEEDS_WIREMOCK_TAG = "NEEDS_CLEAR_COLLECTION";

    @BeforeEach
    void setup(TestInfo testInfo){
        var tags = testInfo.getTags();
        RestAssured.port = this.port;
        mongoTemplate.dropCollection(REVIEWS_COLLECTION_NAME);
        if(tags.contains(NEEDS_WIREMOCK_TAG)) {
            wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8086));
            wireMockServer.start();
        }
        if (!tags.contains(NEEDS_CLEAR_COLLECTION_TAG)) {
            mongoTemplate.insert(ratingEntity);
        }
    }

    @AfterEach
    void stopWireMock(TestInfo testInfo) {
        if(testInfo.getTags().contains(NEEDS_WIREMOCK_TAG))
            wireMockServer.stop();
    }

    @Test
    @Tag(NEEDS_CLEAR_COLLECTION_TAG)
    @Tag(NEEDS_WIREMOCK_TAG)
    void leaveReview_ReturnsRatingResponseDTO() throws JsonProcessingException {
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathMatching("/api/v1/rides/\\d+"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(getRideFullResponseDTO())))
        );

        given()
                .contentType(ContentType.JSON)
                .body(ratingRequestDTO)
                .when()
                .post(LEAVE_REVIEW)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body(RESPONSE_FIELD_RATING, equalTo(Float.valueOf(RESPONSE_FIELD_RATING_VALUE.toString())))
                .body(RESPONSE_FIELD_COMMENT, equalTo(RESPONSE_FIELD_COMMENT_VALUE));
    }

    @Test
    void leaveReview_ThrowsReviewAlreadyExistsException() {
        given()
                .contentType(ContentType.JSON)
                .body(ratingRequestDTO)
                .when()
                .post(LEAVE_REVIEW)
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @Tag(NEEDS_CLEAR_COLLECTION_TAG)
    @Tag(NEEDS_WIREMOCK_TAG)
    void leaveReview_ThrowsRideNotAcceptedException() throws JsonProcessingException {
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathMatching("/api/v1/rides/\\d+"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(getRideFullResponseDTOWithNullDriverId())))
        );

        given()
                .contentType(ContentType.JSON)
                .body(ratingRequestDTO)
                .when()
                .post(LEAVE_REVIEW)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Tag(NEEDS_CLEAR_COLLECTION_TAG)
    @Tag(NEEDS_WIREMOCK_TAG)
    void leaveReview_ThrowsRideNotBelongToPassengerException() throws JsonProcessingException {
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathMatching("/api/v1/rides/\\d+"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(getRideFullResponseDTOWithCustomPassengerId())))
        );

        given()
                .contentType(ContentType.JSON)
                .body(ratingRequestDTO)
                .when()
                .post(LEAVE_REVIEW)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Tag(NEEDS_CLEAR_COLLECTION_TAG)
    @Tag(NEEDS_WIREMOCK_TAG)
    void leaveReview_ThrowsRideNotBelongToDriverException() throws JsonProcessingException {
        wireMockServer.stubFor(WireMock.get(WireMock.urlPathMatching("/api/v1/rides/\\d+"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(getRideFullResponseDTOWithCustomDriverId())))
        );

        given()
                .contentType(ContentType.JSON)
                .body(ratingRequestDTO)
                .when()
                .post(LEAVE_REVIEW)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void viewLeavedReviews_ReturnsPageOfRatingResponseDTO() {
        given()
                .queryParam(REQUEST_PARAM_REVIEWER_TYPE, ratingResponseDTO.reviewerType())
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_LEAVED_REVIEWS, ratingResponseDTO.reviewerId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(RESPONSE_PAGE_ELEMENTS_COUNT_FIELD, equalTo(RESPONSE_PAGE_ELEMENTS_COUNT));
    }

    @Test
    @Tag(NEEDS_CLEAR_COLLECTION_TAG)
    void viewLeavedReviews_ThrowsReviewsNotFoundException() {
        given()
                .queryParam(REQUEST_PARAM_REVIEWER_TYPE, ratingResponseDTO.reviewerType())
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_LEAVED_REVIEWS, ratingResponseDTO.reviewerId())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void getReviewById_ReturnsRatingResponseDTO() throws JsonProcessingException {
        given()
                .when()
                .get(GET_REVIEW_BY_ID, ratingResponseDTO.id().toString())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(ratingResponseDTO)));
    }

    @Test
    void getReviewById_ThrowsIdIsNotValidException() {
        given()
                .when()
                .get(GET_REVIEW_BY_ID, REVIEW_INVALID_ID)
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @Tag(NEEDS_CLEAR_COLLECTION_TAG)
    void getReviewById_ThrowsReviewNotFoundByIdException() {
        given()
                .when()
                .get(GET_REVIEW_BY_ID, NON_EXISTENT_ID.toString())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
