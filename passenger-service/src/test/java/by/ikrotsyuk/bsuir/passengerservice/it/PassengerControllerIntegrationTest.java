package by.ikrotsyuk.bsuir.passengerservice.it;

import by.ikrotsyuk.bsuir.communicationparts.event.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerRequestDTO;
import by.ikrotsyuk.bsuir.passengerservice.dto.PassengerResponseDTO;
import by.ikrotsyuk.bsuir.passengerservice.entity.PassengerEntity;
import by.ikrotsyuk.bsuir.passengerservice.feign.PassengerAccountClient;
import by.ikrotsyuk.bsuir.passengerservice.repository.PassengerRepository;
import by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;

import java.util.List;

import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.ADD_PASSENGER;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.DELETE_PASSENGER_PROFILE_BY_ID;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.GET_ALL_PASSENGERS;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.GET_PASSENGER_PROFILE_BY_ID;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.GET_PASSENGER_RATING_BY_ID;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.NON_EXISTENT_ID;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.PASSENGER_PAYMENT_TYPE_CARD;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.REQUEST_PARAM_DIRECTION;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.REQUEST_PARAM_FIELD;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.REQUEST_PARAM_ITEMS;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.REQUEST_PARAM_OFFSET;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.REQUEST_PARAM_PAYMENT_TYPE;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.UPDATE_PASSENGER_PROFILE_BY_ID;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.UPDATE_PAYMENT_TYPE;
import static by.ikrotsyuk.bsuir.passengerservice.utils.TestDataGenerator.getPageRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {
        TestDataGenerator.SQL_DELETE_PASSENGERS_TABLE,
        TestDataGenerator.SQL_RESTART_PASSENGERS_SEQUENCE
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PassengerControllerIntegrationTest {
    private final PassengerRequestDTO passengerRequestDTO = TestDataGenerator.getPassengerRequestDTO();
    private final PassengerEntity customPassengerEntity = TestDataGenerator.getCustomPassengerEntity();
    private final PassengerEntity integrationPassengerEntity = TestDataGenerator.getIntegrationPassengerEntity();
    private final PassengerResponseDTO passengerResponseDTO = TestDataGenerator.getPassengerResponseDTO();
    private final PassengerResponseDTO cleanPassengerResponseDTO = TestDataGenerator.getCleanPassengerResponseDTO();

    @MockitoBean
    private PassengerAccountClient passengerAccountClient;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PassengerRepository passengerRepository;

    private final String NEEDS_CLEAN_PASSENGERS_TABLE_TAG = "CLEAR_PASSENGERS_TABLE";
    private final String NEEDS_SECOND_PASSENGER_TAG = "NEEDS_SECOND_PASSENGER";
    private final String NEEDS_DELETED_PASSENGER_TAG = "PASSENGER_IS_DELETED";

    @LocalServerPort
    private int port;

    @Container
    @ServiceConnection
    public static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("pgvector/pgvector:pg16")
            .withDatabaseName("taxidb")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        Startables.deepStart(postgresContainer).join();

        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgresContainer::getDriverClassName);
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        var tags = testInfo.getTags();
        RestAssured.port = this.port;
        if(!tags.contains(NEEDS_CLEAN_PASSENGERS_TABLE_TAG)) {
            if(tags.contains(NEEDS_DELETED_PASSENGER_TAG)){
                integrationPassengerEntity.setIsDeleted(true);
                passengerRepository.saveAndFlush(integrationPassengerEntity);
                integrationPassengerEntity.setIsDeleted(false);
            }else
                passengerRepository.save(integrationPassengerEntity);
            if(tags.contains(NEEDS_SECOND_PASSENGER_TAG))
                passengerRepository.save(customPassengerEntity);
        }
    }

    @Test
    void getPassengerProfile_ReturnsPassengerResponseDTO() throws JsonProcessingException {
        given()
                .when()
                .get(GET_PASSENGER_PROFILE_BY_ID, passengerResponseDTO.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(passengerResponseDTO)));
    }

    @Test
    void getPassengerProfile_ThrowsPassengerNotFoundByIdException() {
        given()
                .when()
                .get(GET_PASSENGER_PROFILE_BY_ID, NON_EXISTENT_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void getPassengerRatingById_ReturnsDouble() throws JsonProcessingException {
        given()
                .when()
                .get(GET_PASSENGER_RATING_BY_ID, passengerResponseDTO.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(passengerResponseDTO.rating())));
    }

    @Test
    void getPassengerRatingById_ThrowsPassengerNotFoundByIdException() {
        given()
                .when()
                .get(GET_PASSENGER_RATING_BY_ID, NON_EXISTENT_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void editPassengerProfile_ReturnsPassengerResponseDTO() throws JsonProcessingException {
        given()
                .contentType(ContentType.JSON)
                .body(passengerRequestDTO)
                .when()
                .put(UPDATE_PASSENGER_PROFILE_BY_ID, passengerResponseDTO.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(passengerResponseDTO)));
    }

    @Test
    void editPassengerProfile_ThrowsPassengerNotFoundByIdException() {
        given()
                .contentType(ContentType.JSON)
                .body(passengerRequestDTO)
                .when()
                .put(UPDATE_PASSENGER_PROFILE_BY_ID, NON_EXISTENT_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Tag(NEEDS_SECOND_PASSENGER_TAG)
    void editPassengerProfile_ThrowsPassengerWithSameEmailAlreadyExistsException() {
        PassengerRequestDTO requestDTO = new PassengerRequestDTO(
                passengerRequestDTO.name(),
                TestDataGenerator.getPASSENGER_CUSTOM_EMAIL(),
                passengerRequestDTO.phone()
        );

        given()
                .contentType(ContentType.JSON)
                .body(requestDTO)
                .when()
                .put(UPDATE_PASSENGER_PROFILE_BY_ID, passengerResponseDTO.id())
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @Tag(NEEDS_SECOND_PASSENGER_TAG)
    void editPassengerProfile_ThrowsPassengerWithSamePhoneAlreadyExistsException() {
        PassengerRequestDTO requestDTO = new PassengerRequestDTO(
                passengerRequestDTO.name(),
                passengerRequestDTO.email(),
                TestDataGenerator.getPASSENGER_CUSTOM_PHONE()
        );

        given()
                .contentType(ContentType.JSON)
                .body(requestDTO)
                .when()
                .put(UPDATE_PASSENGER_PROFILE_BY_ID, passengerResponseDTO.id())
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }
    
    @Test
    void deletePassengerProfile_ReturnsPassengerResponseDTO() throws JsonProcessingException {
        given()
                .when()
                .delete(DELETE_PASSENGER_PROFILE_BY_ID, integrationPassengerEntity.getId())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
    
    @Test
    void deletePassengerProfile_ThrowsPassengerNotFoundByIdException() {
        given()
                .when()
                .delete(DELETE_PASSENGER_PROFILE_BY_ID, NON_EXISTENT_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
    
    @Test
    @Tag(NEEDS_DELETED_PASSENGER_TAG)
    void deletePassengerProfile_ThrowsPassengerAlreadyDeletedException() {
        given()
                .when()
                .delete(DELETE_PASSENGER_PROFILE_BY_ID, passengerResponseDTO.id())
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @Tag(NEEDS_CLEAN_PASSENGERS_TABLE_TAG)
    void addPassenger_ReturnsPassengerResponseDTO() throws JsonProcessingException {
        when(passengerAccountClient.createAccount(anyLong(), any(AccountTypes.class)))
                .thenReturn(ResponseEntity.ok(passengerResponseDTO.id()));

        given()
                .contentType(ContentType.JSON)
                .body(passengerRequestDTO)
                .when()
                .post(ADD_PASSENGER)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(cleanPassengerResponseDTO)));

        verify(passengerAccountClient)
                .createAccount(anyLong(), any(AccountTypes.class));
    }

    @Test
    void addPassengerWhenExists_ThrowsPassengerWithSameEmailAlreadyExistsException() {
        given()
                .contentType(ContentType.JSON)
                .body(passengerRequestDTO)
                .when()
                .post(ADD_PASSENGER)
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @Tag(NEEDS_SECOND_PASSENGER_TAG)
    void addPassenger_ThrowsPassengerWithSameEmailAlreadyExistsException() {
        PassengerRequestDTO requestDTO = new PassengerRequestDTO(
                passengerRequestDTO.name(),
                TestDataGenerator.getPASSENGER_CUSTOM_EMAIL(),
                passengerRequestDTO.phone()
        );

        given()
                .contentType(ContentType.JSON)
                .body(requestDTO)
                .when()
                .post(ADD_PASSENGER)
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @Tag(NEEDS_SECOND_PASSENGER_TAG)
    void addPassenger_ThrowsPassengerWithSamePhoneAlreadyExistsException() {
        PassengerRequestDTO requestDTO = new PassengerRequestDTO(
                passengerRequestDTO.name(),
                passengerRequestDTO.email(),
                TestDataGenerator.getPASSENGER_CUSTOM_PHONE()
        );

        given()
                .contentType(ContentType.JSON)
                .body(requestDTO)
                .when()
                .post(ADD_PASSENGER)
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void getAllPassengers_ReturnsPageOfPassengerResponseDTO() throws JsonProcessingException {
        given()
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_ALL_PASSENGERS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(new PageImpl<>(List.of(passengerResponseDTO), getPageRequest(), 1))));
    }

    @Test
    @Tag(NEEDS_CLEAN_PASSENGERS_TABLE_TAG)
    void getAllPassengers_ThrowsPassengersNotFoundException() {
        given()
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_ALL_PASSENGERS)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void changePaymentType_ReturnsPassengerResponseDTO() throws JsonProcessingException {
        PassengerResponseDTO responseDTO = TestDataGenerator.getPassengerResponseDTOWithChangedPaymentType();

        given()
                .queryParam(REQUEST_PARAM_PAYMENT_TYPE, PASSENGER_PAYMENT_TYPE_CARD.name())
                .when()
                .patch(UPDATE_PAYMENT_TYPE, responseDTO.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    void changePaymentType_ThrowsPassengerNotFoundByIdException() {
        given()
                .queryParam(REQUEST_PARAM_PAYMENT_TYPE, PASSENGER_PAYMENT_TYPE_CARD.name())
                .when()
                .patch(UPDATE_PAYMENT_TYPE, NON_EXISTENT_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
