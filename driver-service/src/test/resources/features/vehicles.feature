Feature: Vehicles api

  Scenario: Add a new vehicle
    Given Driver id "1"
    Given Vehicle create info:
    """
                        {
                            "brand": "Audi",
                            "model": "RS7",
                            "carClass": "BUSINESS",
                            "year": "2025",
                            "licensePlate": "8888XM8",
                            "color": "Nardo gray"
                        }
    """
    When Send vehicle POST request to URL
    Then Vehicle response status is 201
    And Vehicle response must have field "id" with value 1
    And Vehicle response must have field "licensePlate" with value "8888XM8"

  Scenario: Get vehicle by vehicleId
    Given Vehicle id "1"
    When Send vehicle GET by id request
    Then Vehicle response status is 200
    And Vehicle response must have field "brand" with value "Audi"
    And Vehicle response must have field "model" with value "RS7"

  Scenario: Get all vehicles
    When Send all vehicles GET request with params "?offset=0&itemCount=10&field=ID&isSortDirectionAsc=true"
    Then Vehicle response status is 200

  Scenario: Get vehicle by type
    When Send vehicle GET by type "?type=BUSINESS&offset=0&itemCount=10&field=ID&isSortDirectionAsc=true"
    Then Vehicle response status is 200
    And Vehicle page response must have field "brand" with value "Audi"
    And Vehicle page response must have field "model" with value "RS7"

  Scenario: Get vehicle by year
    When Send vehicle GET by year "?year=2025&offset=0&itemCount=10&field=ID&isSortDirectionAsc=true"
    Then Vehicle response status is 200
    And Vehicle page response must have field "brand" with value "Audi"
    And Vehicle page response must have field "model" with value "RS7"

  Scenario: Get vehicle by brand
    When Send vehicle GET by brand "?brand=Audi&offset=0&itemCount=10&field=ID&isSortDirectionAsc=true"
    Then Vehicle response status is 200
    And Vehicle page response must have field "brand" with value "Audi"
    And Vehicle page response must have field "model" with value "RS7"

  Scenario: Get vehicle by licensePlate
    When Send vehicle GET by license plate "?licensePlate=8888XM8"
    Then Vehicle response status is 200
    And Vehicle response must have field "brand" with value "Audi"
    And Vehicle response must have field "model" with value "RS7"

  Scenario: Get all driver vehicles
    Given Driver id "1"
    When Send vehicle GET by driver id request
    Then Vehicle response status is 200
    And Vehicle list response must have field "brand" with value "Audi"
    And Vehicle list response must have field "model" with value "RS7"

  Scenario: Get driver current vehicle
    Given Driver id "1"
    When Send vehicle GET current by driver id request
    Then Vehicle response status is 200
    And Vehicle response must have field "brand" with value "Audi"
    And Vehicle response must have field "model" with value "RS7"

  Scenario: Delete vehicle
    Given Driver id "1"
    Given Vehicle id "1"
    When Send vehicle DELETE request
    Then Vehicle response status is 204