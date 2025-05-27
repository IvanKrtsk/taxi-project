package by.ikrotsyuk.bsuir.driverservice.e2e.steps;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.GET_DRIVER_PROFILE_BY_ID;
import static by.ikrotsyuk.bsuir.driverservice.utils.TestDataGenerator.baseUri;
import static io.restassured.RestAssured.given;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DriverSteps {
    @LocalServerPort
    private int port;

    @BeforeAll
    void setUp(){
        RestAssured.port = this.port;
    }

    private Response response;

    private String driverRequestDTO;
    private String vehicleRequestDTO;

    @When("Driver create info")
    public void driver_create_info(){
        driverRequestDTO =
                """
                {
                    "name": "John Doe",
                    "email": "john@gmail.com",
                    "phone": "+375294590134"
                }
                """;
    }

    @When("Driver update info")
    public void driver_update_info(){
        driverRequestDTO =
                """
                {
                    "name": "John Snow",
                    "email": "snow@gmail.com",
                    "phone": "+375294590133"
                }
                """;
    }

    @Given("Driver with id {string}")
    public void driver_with_id(String id) {
        response = given()
                .baseUri(baseUri)
                .get(GET_DRIVER_PROFILE_BY_ID, id);
    }

    @When("Send GET request to URL {string}")
    public void send_get_request_to_url(String endpoint) {
        response = given()
                .baseUri(baseUri)
                .get(endpoint);
    }

    @Then("The response status is 200")
    public void the_response_status_is_200() {
        response.then().statusCode(HttpStatus.OK.value());
    }
}
