package by.ikrotsyuk.bsuir.driverservice.e2e.steps;

import by.ikrotsyuk.bsuir.driverservice.e2e.steps.config.CucumberTestConfig;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.ADD_DRIVER;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_ALL_DRIVERS;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_DRIVER_PROFILE_BY_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_DRIVER_RATING_BY_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.UPDATE_DRIVER_BY_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.baseUri;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = CucumberTestConfig.class)
public class DriverSteps {
    @LocalServerPort
    private int port;

    private Response response;
    private WireMockServer wireMockServer;
    private String driverCreatePayload;
    private String driverUpdatePayload;
    private String id;

    @Given("Driver create info:")
    public void driverCreateInfo(String payload) {
        driverCreatePayload = payload;
    }

    @Given("Driver update info:")
    public void driverUpdateInfo(String payload) {
        driverUpdatePayload = payload;
    }

    @Given("Driver with id {string}")
    public void driverWithId(String id) {
        this.id = id;
    }

    @When("Send driver profile GET request")
    public void sendDriverProfileGETRequestToURL() {
        response = given()
                .baseUri(baseUri + port)
                .get(GET_DRIVER_PROFILE_BY_ID, id);
    }

    @When("Send driver rating GET request")
    public void sendDriverRatingGETRequest() {
        response = given()
                .baseUri(baseUri + port)
                .get(GET_DRIVER_RATING_BY_ID, id);
    }

    @When("Send all drivers GET request with params {string}")
    public void sendAllDriversGETRequest(String params) {
        response = given()
                .baseUri(baseUri + port)
                .get(GET_ALL_DRIVERS + params);
    }

    @When("Send driver POST request to URL")
    public void sendPostRequestToUrl() {
        wireMockSetUp();

        response = given()
                .baseUri(baseUri + port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(driverCreatePayload)
                .post(ADD_DRIVER);

        wireMockStop();
    }

    @When("Send driver PUT request to URL")
    public void sendPUTRequestToURL() {
        response = given()
                .baseUri(baseUri + port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(driverUpdatePayload)
                .put(UPDATE_DRIVER_BY_ID, id);
    }

    @When("Send DELETE request to URL {string}")
    public void sendDELETERequestToURL(String endpoint) {
        response = given()
                .baseUri(baseUri + port)
                .put(endpoint);
    }

    @Then("The response status is {int}")
    public void theResponseStatusIsInt(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @And("Driver response must have field {string} with value {int}")
    public void responseMustHaveId(String key, int value) {
        response
                .then()
                .body(key, equalTo(value));
    }

    @And("Driver response must have field {string} with value {string}")
    public void responseMustHaveFieldWithValue(String key, String value) {
        response
                .then()
                .body(key, equalTo(value));
    }

    @And("Driver response must have rating {double}")
    public void responseMustHaveFieldWithValue(double rating) {
        response
                .then()
                .body(equalTo(String.valueOf(rating)));
    }

    private void wireMockSetUp() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8088));
        wireMockServer.start();
        wireMockStubSetUp();
    }

    private void wireMockStubSetUp() {
        wireMockServer.stubFor(WireMock.post(WireMock.urlPathMatching("/api/v1/users/\\d+/payments/accounts"))
                .withQueryParam("accountType", WireMock.equalTo("DRIVER"))
                .willReturn(WireMock.aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("1")));
    }

    private void wireMockStop() {
        wireMockServer.stop();
    }
}
