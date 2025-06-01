Feature: Passengers api

  Scenario: Add a new passenger
    Given Passenger create info:
    """
                        {
                            "name": "John Doe",
                            "email": "john@gmail.com",
                            "phone": "+375294590134"
                        }
    """
    When Send POST request to URL
    Then The response status is 201
    And Response must have field "id" with value 1
    And Response must have field "name" with value "John Doe"

  Scenario: Get passenger profile by passengerId
    Given Passenger with id "1"
    When Send profile GET request
    Then The response status is 200
    And Response must have field "id" with value 1
    And Response must have field "email" with value "john@gmail.com"

  Scenario: Get passenger rating by passengerId
    Given Passenger with id "1"
    When Send rating GET request
    Then The response status is 200
    And Response must have rating 0.0

  Scenario: Get all passengers
    When Send all passengers GET request with params "?offset=0&itemCount=10&field=ID&isSortDirectionAsc=true"
    Then The response status is 200

  Scenario: Edit passenger
    Given Passenger with id "1"
    Given Passenger update info:
    """
                        {
                            "name": "John Snow",
                            "email": "snow@gmail.com",
                            "phone": "+375294590133"
                        }
    """
    When Send PUT request to URL
    Then The response status is 200
    And Response must have field "email" with value "snow@gmail.com"

  Scenario: Edit passenger payment type
    Given Passenger with id "1"
    When Send PUT request with params "?paymentType=CARD"
    Then The response status is 200
    And Response must have field "paymentType" with value "CARD"

  Scenario: Delete an existent passenger
    Given Passenger with id "1"
    When Send DELETE request to URL "api/v1/passengers/1"
    Then The response status is 400