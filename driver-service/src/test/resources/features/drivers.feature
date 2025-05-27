Feature: Drivers api

  Scenario: Get driver profile by driverId
    Given Driver with id "1"
    When Send GET request to URL "/api/v1/drivers/1/profile"
    Then The response status is 200

#  Scenario: Get driver rating by driverId
#    Given Driver with id "1"
#    When Send GET request to URL "/api/v1/drivers/1/rating"
#    Then The response status is 200
#
#  Scenario: Get all drivers
#    When Send GET request to URL "/api/v1/drivers?offset=0&itemCount=10&field=ID&isSortDirectionAsc=true"
#    Then The response status is 200
#
#  Scenario: Get driver profile with vehicles
#    Given Driver with id "1"
#    When Send GET request to URL "/api/v1/drivers/1/vehicles"
#    Then The response status is 200
#
#  Scenario: Add a new driver
#    Given Driver create info
#    When Send POST request to URL "/api/v1/drivers"
#    Then The response status is 201
#
#  Scenario: Edit driver
#    Given Driver update info
#    When Send PUT request to URL "/api/v1/drivers/1"
#    Then The response ststus is 200
#
#  Scenario: Delete an existent driver
#    Given Driver id "1"
#    When Send DELETE request to URL "/api/v1/drivers/1"
#    Then The response status is 200