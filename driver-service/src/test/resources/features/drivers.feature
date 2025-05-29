Feature: Drivers api

  Scenario: Add a new driver
    Given Driver create info:
    """
                        {
                            "name": "John Doe",
                            "email": "john@gmail.com",
                            "phone": "+375294590134"
                        }
    """
    When Send driver POST request to URL
    Then The response status is 201
    And Driver response must have field "id" with value 1
    And Driver response must have field "name" with value "John Doe"

  Scenario: Get driver profile by driverId
    Given Driver with id "1"
    When Send driver profile GET request
    Then The response status is 200
    And Driver response must have field "id" with value 1
    And Driver response must have field "email" with value "john@gmail.com"

  Scenario: Get driver rating by driverId
    Given Driver with id "1"
    When Send driver rating GET request
    Then The response status is 200
    And Driver response must have rating 0.0

  Scenario: Get all drivers
    When Send all drivers GET request with params "?offset=0&itemCount=10&field=ID&isSortDirectionAsc=true"
    Then The response status is 200

  Scenario: Edit driver
    Given Driver with id "1"
    Given Driver update info:
    """
                        {
                            "name": "John Snow",
                            "email": "snow@gmail.com",
                            "phone": "+375294590133"
                        }
    """
    When Send driver PUT request to URL
    Then The response status is 200

  Scenario: Delete an existent driver
    Given Driver with id "1"
    When Send DELETE request to URL "/api/v1/drivers/1"
    Then The response status is 400