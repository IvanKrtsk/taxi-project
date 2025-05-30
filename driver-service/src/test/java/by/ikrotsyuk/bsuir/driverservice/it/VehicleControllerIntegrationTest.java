package by.ikrotsyuk.bsuir.driverservice.it;

import by.ikrotsyuk.bsuir.driverservice.dto.VehicleRequestDTO;
import by.ikrotsyuk.bsuir.driverservice.dto.VehicleResponseDTO;
import by.ikrotsyuk.bsuir.driverservice.entity.DriverEntity;
import by.ikrotsyuk.bsuir.driverservice.entity.VehicleEntity;
import by.ikrotsyuk.bsuir.driverservice.repository.DriverRepository;
import by.ikrotsyuk.bsuir.driverservice.repository.VehicleRepository;
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
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startables;

import java.util.List;

import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.ADD_VEHICLE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.CHOOSE_CURRENT_VEHICLE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.DELETE_VEHICLE_BY_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_ALL_DRIVER_VEHICLES;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_ALL_VEHICLES;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_ALL_VEHICLES_BY_BRAND;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_ALL_VEHICLES_BY_TYPE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_ALL_VEHICLES_BY_YEAR;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_DRIVER_CURRENT_VEHICLE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_VEHICLE_BY_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_VEHICLE_BY_LICENSE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.NON_EXISTENT_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_BRAND;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_DIRECTION;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_FIELD;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_ITEMS;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_LICENSE_PLATE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_OFFSET;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_TYPE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.REQUEST_PARAM_YEAR;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.UPDATE_VEHICLE;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.getCustomVehicleRequestDTO;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.getCustomVehicleResponseDTO;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.getModifiedVehicleResponseDTO;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.getPageRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {
        TestDataGenerator.SQL_DELETE_DRIVERS_TABLE,
        TestDataGenerator.SQL_DELETE_VEHICLES_TABLE,
        TestDataGenerator.SQL_RESTART_DRIVERS_SEQUENCE,
        TestDataGenerator.SQL_RESTART_VEHICLES_SEQUENCE
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class VehicleControllerIntegrationTest {
    private final VehicleEntity vehicleEntity = TestDataGenerator.getVehicleEntity();
    private final VehicleEntity integrationVehicleEntity = TestDataGenerator.getIntegrationVehicleEntity();
    private final VehicleRequestDTO vehicleRequestDTO = TestDataGenerator.getVehicleRequestDTO();
    private final VehicleRequestDTO customVehicleRequestDTO = getCustomVehicleRequestDTO();
    private final VehicleResponseDTO vehicleResponseDTO = TestDataGenerator.getVehicleResponseDTO(true);
    private final DriverEntity driverEntity = TestDataGenerator.getDriverEntity();
    private final DriverEntity integrationDriverEntity = TestDataGenerator.getIntegrationDriverEntity();
    private final VehicleEntity vehicleDriverEntity = TestDataGenerator.getVehicleDriverEntity(driverEntity);

    private final String NEEDS_VEHICLE_TAG = "NEEDS_SAME_VEHICLE";
    private final String NEEDS_VEHICLE_DRIVER_TAG = "NEEDS_VEHICLE_DRIVER";
    private final String NEEDS_NON_CURRENT_VEHICLE = "NEEDS_NON_CURRENT_VEHICLE";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private DriverRepository driverRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        var tags = testInfo.getTags();
        RestAssured.port = this.port;
        driverRepository.save(integrationDriverEntity);
        if(!tags.contains(NEEDS_NON_CURRENT_VEHICLE)) {
            if (tags.contains(NEEDS_VEHICLE_TAG))
                vehicleRepository.save(integrationVehicleEntity);
            if (tags.contains(NEEDS_VEHICLE_DRIVER_TAG))
                vehicleRepository.saveAndFlush(vehicleDriverEntity);
        } else {
            integrationVehicleEntity.setIsCurrent(false);
            vehicleRepository.saveAndFlush(integrationVehicleEntity);
            integrationVehicleEntity.setIsCurrent(true);
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
    void addVehicle_ReturnsVehicleResponseDTO() throws JsonProcessingException {
        VehicleResponseDTO customVehicleResponseDTO = getCustomVehicleResponseDTO();
        given()
                .contentType(ContentType.JSON)
                .body(vehicleRequestDTO)
                .when()
                .post(ADD_VEHICLE, driverEntity.getId())
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body(equalTo(objectMapper.writeValueAsString(customVehicleResponseDTO)));
    }

    @Test
    void addVehicle_ThrowsDriverNotFoundByIdException(){
        given()
                .contentType(ContentType.JSON)
                .body(vehicleRequestDTO)
                .when()
                .post(ADD_VEHICLE, NON_EXISTENT_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Tag(NEEDS_VEHICLE_TAG)
    void addVehicle_ThrowsVehicleWithSameLicensePlateAlreadyExistsException(){
        given()
                .contentType(ContentType.JSON)
                .body(vehicleRequestDTO)
                .when()
                .post(ADD_VEHICLE, driverEntity.getId())
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @Tag(NEEDS_VEHICLE_DRIVER_TAG)
    void editVehicle_ReturnsVehicleResponseDTO() throws JsonProcessingException {
        VehicleResponseDTO vehicleResponseDTO1 = getModifiedVehicleResponseDTO();
        given()
                .contentType(ContentType.JSON)
                .body(customVehicleRequestDTO)
                .when()
                .patch(UPDATE_VEHICLE, driverEntity.getId(), vehicleEntity.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(equalTo(objectMapper.writeValueAsString(vehicleResponseDTO1)));
    }

    @Test
    @Tag(NEEDS_VEHICLE_DRIVER_TAG)
    void editVehicle_ThrowsDriverNotFoundByIdException(){
        given()
                .contentType(ContentType.JSON)
                .body(customVehicleRequestDTO)
                .when()
                .patch(UPDATE_VEHICLE, NON_EXISTENT_ID, vehicleEntity.getId())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Tag(NEEDS_VEHICLE_DRIVER_TAG)
    void editVehicle_ThrowsVehicleNotFoundByIdException(){
        given()
                .contentType(ContentType.JSON)
                .body(customVehicleRequestDTO)
                .when()
                .patch(UPDATE_VEHICLE, driverEntity.getId(), NON_EXISTENT_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Tag(NEEDS_VEHICLE_DRIVER_TAG)
    void editVehicle_ThrowsVehicleWithSameLicensePlateAlreadyExistsException(){
        given()
                .contentType(ContentType.JSON)
                .body(vehicleRequestDTO)
                .when()
                .patch(UPDATE_VEHICLE, driverEntity.getId(), vehicleEntity.getId())
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @Tag(NEEDS_VEHICLE_DRIVER_TAG)
    void chooseCurrentVehicle_ReturnsVehicleResponseDTO() throws JsonProcessingException {
        given()
                .when()
                .patch(CHOOSE_CURRENT_VEHICLE, driverEntity.getId(), vehicleEntity.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(vehicleResponseDTO)));
    }

    @Test
    void chooseCurrentVehicle_ThrowsDriverVehiclesNotFoundException() {
        given()
                .when()
                .patch(CHOOSE_CURRENT_VEHICLE, driverEntity.getId(), vehicleEntity.getId())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Tag(NEEDS_VEHICLE_DRIVER_TAG)
    void getVehicleById_ReturnsVehicleResponseDTO() throws JsonProcessingException {
        given()
                .when()
                .get(GET_VEHICLE_BY_ID, vehicleEntity.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(vehicleResponseDTO)));
    }

    @Test
    void getVehicleById_ThrowsVehicleNotFoundByIdException(){
        given()
                .when()
                .get(GET_VEHICLE_BY_ID, vehicleEntity.getId())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Tag(NEEDS_VEHICLE_DRIVER_TAG)
    void getAllVehicles_ReturnsPageOfVehicleResponseDTO() throws JsonProcessingException {
        given()
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_ALL_VEHICLES)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(new PageImpl<>(List.of(vehicleResponseDTO), getPageRequest(), 1))));
    }

    @Test
    void getAllVehicles_ThrowsVehiclesNotFoundException() {
        given()
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_ALL_VEHICLES)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Tag(NEEDS_VEHICLE_DRIVER_TAG)
    void getAllVehiclesByType_ReturnsPageOfVehicleResponseDTO() throws JsonProcessingException {
        given()
                .queryParam(REQUEST_PARAM_TYPE, String.valueOf(vehicleResponseDTO.carClass()))
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_ALL_VEHICLES_BY_TYPE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(new PageImpl<>(List.of(vehicleResponseDTO), getPageRequest(), 1))));
    }

    @Test
    void getAllVehiclesByType_ThrowsVehiclesNotFoundByTypeException(){
        given()
                .queryParam(REQUEST_PARAM_TYPE, String.valueOf(vehicleResponseDTO.carClass()))
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_ALL_VEHICLES_BY_TYPE)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Tag(NEEDS_VEHICLE_DRIVER_TAG)
    void getAllVehiclesByYear_ReturnsPageOfVehicleResponseDTO() throws JsonProcessingException {
        given()
                .queryParam(REQUEST_PARAM_YEAR, String.valueOf(vehicleResponseDTO.year()))
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_ALL_VEHICLES_BY_YEAR)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(new PageImpl<>(List.of(vehicleResponseDTO), getPageRequest(), 1))));
    }

    @Test
    void getAllVehiclesByYear_ThrowsVehiclesNotFoundByYearException() {
        given()
                .queryParam(REQUEST_PARAM_YEAR, String.valueOf(vehicleResponseDTO.year()))
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_ALL_VEHICLES_BY_YEAR)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Tag(NEEDS_VEHICLE_DRIVER_TAG)
    void getAllVehiclesByBrand_ReturnsPageOfVehicleResponseDTO() throws JsonProcessingException {
        given()
                .queryParam(REQUEST_PARAM_BRAND, vehicleResponseDTO.brand())
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_ALL_VEHICLES_BY_BRAND)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(new PageImpl<>(List.of(vehicleResponseDTO), getPageRequest(), 1))));
    }

    @Test
    void getAllVehiclesByBrand_ThrowsVehiclesNotFoundByBrandException(){
        given()
                .queryParam(REQUEST_PARAM_BRAND, vehicleResponseDTO.brand())
                .queryParam(REQUEST_PARAM_OFFSET, TestDataGenerator.getDEFAULT_PAGE())
                .queryParam(REQUEST_PARAM_ITEMS, TestDataGenerator.getDEFAULT_ITEMS_PER_PAGE_COUNT())
                .queryParam(REQUEST_PARAM_FIELD, TestDataGenerator.getDEFAULT_SORT_FIELD())
                .queryParam(REQUEST_PARAM_DIRECTION, true)
                .when()
                .get(GET_ALL_VEHICLES_BY_BRAND)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Tag(NEEDS_VEHICLE_DRIVER_TAG)
    void getVehicleByLicense_ReturnsVehicleResponseDTO() throws JsonProcessingException {
        given()
                .queryParam(REQUEST_PARAM_LICENSE_PLATE, vehicleResponseDTO.licensePlate())
                .when()
                .get(GET_VEHICLE_BY_LICENSE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(vehicleResponseDTO)));
    }

    @Test
    void getVehicleByLicense_ThrowsVehicleNotFoundByLicensePlateException() {
        given()
                .queryParam(REQUEST_PARAM_LICENSE_PLATE, vehicleResponseDTO.licensePlate())
                .when()
                .get(GET_VEHICLE_BY_LICENSE)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Tag(NEEDS_VEHICLE_DRIVER_TAG)
    void deleteVehicleById_ReturnsVehicleResponseDTO() {
        given()
                .when()
                .delete(DELETE_VEHICLE_BY_ID, driverEntity.getId(), vehicleEntity.getId())
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deleteVehicleById_ThrowsVehicleNotFoundByIdException() {
        given()
                .when()
                .delete(DELETE_VEHICLE_BY_ID, driverEntity.getId(), NON_EXISTENT_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Tag(NEEDS_VEHICLE_DRIVER_TAG)
    void getAllDriverVehicles_ReturnsListOfVehicleResponseDTO() throws JsonProcessingException {
        given()
                .when()
                .get(GET_ALL_DRIVER_VEHICLES, driverEntity.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(List.of(vehicleResponseDTO))));
    }

    @Test
    void getAllDriverVehicles_ThrowsDriverNotFoundByIdException() {
        given()
                .when()
                .get(GET_ALL_DRIVER_VEHICLES, NON_EXISTENT_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Tag(NEEDS_VEHICLE_DRIVER_TAG)
    void getDriverCurrentVehicle_ReturnsVehicleResponseDTO() throws JsonProcessingException {
        given()
                .when()
                .get(GET_DRIVER_CURRENT_VEHICLE, driverEntity.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(equalTo(objectMapper.writeValueAsString(vehicleResponseDTO)));
    }

    @Test
    @Tag(NEEDS_NON_CURRENT_VEHICLE)
    void getDriverCurrentVehicle_ThrowsDriverCurrentVehicleNotFoundException() {
        given()
                .when()
                .get(GET_DRIVER_CURRENT_VEHICLE, driverEntity.getId())
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
