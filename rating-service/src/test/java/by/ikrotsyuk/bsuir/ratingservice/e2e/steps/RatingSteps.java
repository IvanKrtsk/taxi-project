package by.ikrotsyuk.bsuir.ratingservice.e2e.steps;

import by.ikrotsyuk.bsuir.ratingservice.e2e.steps.config.CucumberTestConfig;
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

import static by.ikrotsyuk.bsuir.ratingservice.utils.TestDataGenerator.baseUri;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = CucumberTestConfig.class)
public class RatingSteps {
    @LocalServerPort
    private int port;

    private Response response;
    private WireMockServer wireMockServer;
    private String ratingCreatePayload;
    private String ratingUpdatePayload;
    private String id;

    /*@Given("Passenger create info:")
    public void passengerCreateInfo(String payload) {
        passengerCreatePayload = payload;
    }

    @Given("Passenger update info:")
    public void passengerUpdateInfo(String payload) {
        passengerUpdatePayload = payload;
    }

    @Given("Passenger with id {string}")
    public void passengerWithId(String id) {
        this.id = id;
    }

    @Then("The response status is {int}")
    public void theResponseStatusIsInt(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @When("Send profile GET request")
    public void sendPassengerProfileGETRequestToURL() {
        response = given()
                .baseUri(baseUri + port)
                .get(GET_PASSENGER_PROFILE_BY_ID, id);
    }

    @When("Send rating GET request")
    public void sendPassengerRatingGETRequest() {
        response = given()
                .baseUri(baseUri + port)
                .get(GET_PASSENGER_RATING_BY_ID, id);
    }

    @When("Send all passengers GET request with params {string}")
    public void sendAllPassengersGETRequest(String params) {
        response = given()
                .baseUri(baseUri + port)
                .get(GET_ALL_PASSENGERS + params);
    }

    @When("Send POST request to URL")
    public void sendPostRequestToUrl() {
        wireMockSetUp();

        response = given()
                .baseUri(baseUri + port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(passengerCreatePayload)
                .post(ADD_PASSENGER);

        wireMockStop();
    }

    @When("Send PUT request to URL")
    public void sendPUTRequestToURL() {
        response = given()
                .baseUri(baseUri + port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(passengerUpdatePayload)
                .put(UPDATE_PASSENGER_PROFILE_BY_ID, id);
    }

    @When("Send PUT request with params {string}")
    public void sendPUTRequestWithParams(String endpoint) {
        response = given()
                .baseUri(baseUri + port)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .patch(UPDATE_PASSENGER_PROFILE_BY_ID + endpoint, id);
    }

    @When("Send DELETE request to URL {string}")
    public void sendDELETERequestToURL(String endpoint) {
        response = given()
                .baseUri(baseUri + port)
                .put(endpoint);
    }

    @And("Response must have field {string} with value {int}")
    public void responseMustHaveId(String key, int value) {
        response
                .then()
                .body(key, equalTo(value));
    }

    @And("Response must have field {string} with value {string}")
    public void responseMustHaveFieldWithValue(String key, String value) {
        response
                .then()
                .body(key, equalTo(value));
    }

    @And("Response must have rating {double}")
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
                .withQueryParam("accountType", WireMock.equalTo("PASSENGER"))
                .willReturn(WireMock.aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("1")));
    }

    private void wireMockStop() {
        wireMockServer.stop();
    }*/
}
