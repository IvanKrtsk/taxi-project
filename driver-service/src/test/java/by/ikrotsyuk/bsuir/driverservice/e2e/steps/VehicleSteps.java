package by.ikrotsyuk.bsuir.driverservice.e2e.steps;

import by.ikrotsyuk.bsuir.driverservice.e2e.steps.config.CucumberTestConfig;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

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
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.baseUri;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = CucumberTestConfig.class)
public class VehicleSteps {
    @LocalServerPort
    private int port;

    private Response response;
    private String vehicleCreatePayload;
    private String vehicleUpdatePayload;
    private String vehicleId;
    private String driverId;

    @Given("Driver id {string}")
    public void driverId(String id) {
        this.driverId = id;
    }

    @Given("Vehicle id {string}")
    public void vehicleId(String id) {
        this.vehicleId = id;
    }

    @When("Vehicle create info:")
    public void vehicleCreateInfo(String payload) {
        this.vehicleCreatePayload = payload;
    }

    @When("Vehicle update info:")
    public void vehicleUpdatePayload(String payload) {
        this.vehicleUpdatePayload = payload;
    }

    @When("Send vehicle POST request to URL")
    public void sendVehiclePostRequestToUrl() {
        response = given()
                .baseUri(baseUri + port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(vehicleCreatePayload)
                .post(ADD_VEHICLE, driverId);
    }

    @When("Send vehicle GET by id request")
    public void sendVehicleGETByIdRequest() {
        response = given()
                .baseUri(baseUri + port)
                .get(GET_VEHICLE_BY_ID, vehicleId);
    }

    @When("Send all vehicles GET request with params {string}")
    public void sendAllVehiclesGETRequestWithParams(String params) {
        response = given()
                .baseUri(baseUri + port)
                .get(GET_ALL_VEHICLES + params);
    }

    @When("Send vehicle GET by type {string}")
    public void sendVehicleGETByType(String param) {
        response = given()
                .baseUri(baseUri + port)
                .get(GET_ALL_VEHICLES_BY_TYPE + param);
    }

    @When("Send vehicle GET by year {string}")
    public void sendVehicleGETByYearRequest(String param) {
        response = given()
                .baseUri(baseUri + port)
                .get(GET_ALL_VEHICLES_BY_YEAR + param);
    }

    @When("Send vehicle GET by brand {string}")
    public void sendVehicleGETByBrand(String param) {
        response = given()
                .baseUri(baseUri + port)
                .get(GET_ALL_VEHICLES_BY_BRAND + param);
    }

    @When("Send vehicle GET by license plate {string}")
    public void sendVehicleGETByLicensePlate(String param) {
        response = given()
                .baseUri(baseUri + port)
                .get(GET_VEHICLE_BY_LICENSE + param);
    }

    @When("Send vehicle GET by driver id request")
    public void sendVehicleGETByDriverIdRequest() {
        response = given()
                .baseUri(baseUri + port)
                .get(GET_ALL_DRIVER_VEHICLES, driverId);
    }

    @When("Send vehicle GET current by driver id request")
    public void sendVehicleGETCurrentByDriverIdRequest() {
        response = given()
                .baseUri(baseUri + port)
                .get(GET_DRIVER_CURRENT_VEHICLE, driverId);
    }

    @When("Send vehicle PATCH current request")
    public void sendVehiclePATCHCurrentRequest() {
        response = given()
                .baseUri(baseUri + port)
                .put(CHOOSE_CURRENT_VEHICLE, driverId, vehicleId);
    }

    @When("Send vehicle DELETE request")
    public void sendVehicleDELETERequest() {
        response = given()
                .baseUri(baseUri + port)
                .delete(DELETE_VEHICLE_BY_ID, driverId, vehicleId);
    }

    @Then("Vehicle response status is {int}")
    public void vehicleResponseStatusIsInt(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @And("Vehicle response must have field {string} with value {int}")
    public void responseMustHaveId(String key, int value) {
        response
                .then()
                .body(key, equalTo(value));
    }

    @And("Vehicle response must have field {string} with value {string}")
    public void responseMustHaveFieldWithValue(String key, String value) {
        response
                .then()
                .body(key, equalTo(value));
    }

    @And("Vehicle page response must have field {string} with value {string}")
    public void vehiclePageResponseMustHaveFieldWithValue(String key, String value) {
        response
                .then()
                .body("content[0]." + key, equalTo(value));
    }

    @And("Vehicle list response must have field {string} with value {string}")
    public void vehicleListResponseMustHaveFieldWithValue(String key, String value) {
        response
                .then()
                .body(key, equalTo(List.of(value)));
    }
}
