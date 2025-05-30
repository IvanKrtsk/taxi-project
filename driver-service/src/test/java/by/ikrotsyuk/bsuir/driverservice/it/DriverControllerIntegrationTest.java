package by.ikrotsyuk.bsuir.driverservice.it;

import by.ikrotsyuk.bsuir.communicationparts.event.customtypes.AccountTypes;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.DriverResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.feign.DriverAccountClient;
import by.ikrotsyuk.bsuir.driverservice.repository.DriverRepository;
import by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator;
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

import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.ADD_DRIVER;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.DELETE_DRIVER_BY_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_ALL_DRIVERS;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_DRIVER_PROFILE_BY_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_DRIVER_RATING_BY_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_DRIVER_WITH_VEHICLES;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.NON_EXISTENT_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_DIRECTION;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_FIELD;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_ITEMS;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_OFFSET;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.UPDATE_DRIVER_BY_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.getCustomDriverRequestDTO;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.getCustomDriverResponseDTO;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.getPageRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {
        TestDataGenerator.SQL_DELETE_DRIVERS_TABLE,
        TestDataGenerator.SQL_DELETE_VEHICLES_TABLE,
        TestDataGenerator.SQL_RESTART_DRIVERS_SEQUENCE,
        TestDataGenerator.SQL_RESTART_VEHICLES_SEQUENCE
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class DriverControllerIntegrationTest {
    private final DriverResponseDTO driverResponseDTO = TestDataGenerator.getDriverResponseDTO();
    private final DriverEntity driverEntity = TestDataGenerator.getIntegrationDriverEntity();
    private final DriverEntity customDriverEntity = TestDataGenerator.getCustomDriverEntity(TestDataGenerator.getCustomDriverEmail(), TestDataGenerator.getCustomDriverPhone());
    private final DriverRequestDTO driverRequestDTO = TestDataGenerator.getDriverRequestDTO();

    @MockitoBean
    private DriverAccountClient driverAccountClient;

    private final String NEEDS_SECOND_DRIVER_TAG = "SECOND_DRIVER_WITH_CUSTOM_EMAIL_AND_PHONE";
    private final String NEEDS_DELETED_DRIVER_TAG = "DRIVER_IS_DELETED";
    private final String NEEDS_CLEAR_DRIVERS_TABLE_TAG = "CLEAR_DRIVER_TABLE";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DriverRepository driverRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        var tags = testInfo.getTags();
        RestAssured.port = this.port;
        if (!tags.contains(NEEDS_CLEAR_DRIVERS_TABLE_TAG)) {
            if (!tags.contains(NEEDS_DELETED_DRIVER_TAG)) {
                driverRepository.save(driverEntity);
            } else {
                driverEntity.setIsDeleted(true);
                driverRepository.saveAndFlush(driverEntity);
                driverEntity.setIsDeleted(false);
            }
            if (testInfo.getTags().contains(NEEDS_SECOND_DRIVER_TAG))
                driverRepository.save(customDriverEntity);
        }
    }

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

    @Test
    void getDriverProfile_ReturnsDriverResponseDTO() throws Exception {
        given()
                .when()
                .get(GET_DRIVER_PROFILE_BY_ID, driverResponseDTO.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(driverResponseDTO)));
    }

    @Test
    void getDriverProfile_ThrowsDriverNotFoundByIdException() {
        given()
                .when()
                .get(GET_DRIVER_PROFILE_BY_ID, NON_EXISTENT_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void getDriverRating_ReturnsDouble() throws JsonProcessingException {
        given()
                .when()
                .get(GET_DRIVER_RATING_BY_ID, driverResponseDTO.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(driverResponseDTO.rating())));

    }

    @Test
    void getDriverRating_ThrowsDriverNotFoundByIdException() {
        given()
                .when()
                .get(GET_DRIVER_RATING_BY_ID, NON_EXISTENT_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void editDriverProfile_ReturnsDriverResponseDTO() throws JsonProcessingException {
        given()
                .contentType(ContentType.JSON)
                .body(driverRequestDTO)
                .when()
                .put(UPDATE_DRIVER_BY_ID, driverResponseDTO.id())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(driverResponseDTO)));
    }

    @Test
    void editDriverProfile_ThrowsDriverNotFoundByIdException() {
        given()
                .contentType(ContentType.JSON)
                .body(driverRequestDTO)
                .when()
                .put(UPDATE_DRIVER_BY_ID, NON_EXISTENT_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Tag(NEEDS_SECOND_DRIVER_TAG)
    void editDriverProfile_ThrowsDriverWithSameEmailAlreadyExistsException() {
        DriverRequestDTO customDriverRequestDTO = getCustomDriverRequestDTO(
                TestDataGenerator.getCustomDriverEmail(),
                driverEntity.getPhone()
        );
        given()
                .contentType(ContentType.JSON)
                .body(customDriverRequestDTO)
                .when()
                .put(UPDATE_DRIVER_BY_ID, driverResponseDTO.id())
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @Tag(NEEDS_SECOND_DRIVER_TAG)
    void editDriverProfile_ThrowsDriverWithSamePhoneAlreadyExistsException() {
        DriverRequestDTO customDriverRequestDTO = getCustomDriverRequestDTO(
                driverEntity.getEmail(),
                TestDataGenerator.getCustomDriverPhone()
        );
        given()
                .contentType(ContentType.JSON)
                .body(customDriverRequestDTO)
                .when()
                .put(UPDATE_DRIVER_BY_ID, driverResponseDTO.id())
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void deleteDriverProfile_ReturnsDriverResponseDTO(){
        given()
                .when()
                .delete(DELETE_DRIVER_BY_ID, driverResponseDTO.id())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deleteDriverProfile_ThrowsDriverNotFoundByIdException() {
        given()
                .when()
                .delete(DELETE_DRIVER_BY_ID, NON_EXISTENT_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Tag(NEEDS_DELETED_DRIVER_TAG)
    void deleteDriverProfile_ThrowsDriverAlreadyDeletedException() {
        given()
                .when()
                .delete(DELETE_DRIVER_BY_ID, driverResponseDTO.id())
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @Tag(NEEDS_CLEAR_DRIVERS_TABLE_TAG)
    void addDriver_ReturnsDriverResponseDTO() throws JsonProcessingException {
        DriverResponseDTO driverResponseDTO1 = getCustomDriverResponseDTO();
        when(driverAccountClient.createAccount(anyLong(), any(AccountTypes.class)))
                .thenReturn(ResponseEntity.ok(driverEntity.getId()));
        given()
                .contentType(ContentType.JSON)
                .body(driverRequestDTO)
                .when()
                .post(ADD_DRIVER)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(driverResponseDTO1)));

        verify(driverAccountClient)
                .createAccount(anyLong(), any(AccountTypes.class));
    }

    @Test
    void addDriver_ThrowsDriverWithSameEmailAlreadyExistsException() {
        given()
                .contentType(ContentType.JSON)
                .body(driverRequestDTO)
                .when()
                .post(ADD_DRIVER)
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    void getAllDrivers_ReturnsPageOfDriverResponseDTO() throws JsonProcessingException {
        given()
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_ALL_DRIVERS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(new PageImpl<>(List.of(driverResponseDTO), getPageRequest(), 1))));
    }

    @Test
    @Tag(NEEDS_CLEAR_DRIVERS_TABLE_TAG)
    void getAllDrivers_ThrowsDriversNotFoundException() {
        given()
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_ALL_DRIVERS)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void getDriverWithVehicleById_ThrowsDriverNotFoundByIdException() {
        given()
                .when()
                .get(GET_DRIVER_WITH_VEHICLES, NON_EXISTENT_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
