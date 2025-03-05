Feature: Store API validations

  Scenario: Validates creates user list endpoint
    Given The user wants to creates a list of "3" users
    When A POST request is sent to /user/createWithList
    Then The response status code should be 200
    And The response should have a message with "ok"

  Scenario: Validates search user by name endpoint
    Given The user wants to search the username "user1"
    When A GET request is sent to /user/{username}
    Then The response status code should be 200
    And The response should have the username "user1"

  Scenario: Validates update an existing user endpoint
    Given The user wants to update an user with the name "user1"
    When A PUT request is sent to /user/{username}
    Then The response status code should be 200
    And The response should have a message with userid

  Scenario: Validates delete an user by username endpoint
    Given The user wants to delete the user with name "user2"
    When A DELETE request is sent to /user/{username}
    Then The response status code should be 200
    And The response should have a message with "user2"

  Scenario: Validates login user endpoint
    Given The user wants to logs user into the system with the username "user1" and password "1230"
    When A GET request is sent to /user/login
    Then The response status code should be 200
    And The response should have a message with "logged in user session:"

  Scenario: Validates creates user array endpoint
    Given The user wants to creates an array of "3" users
    When A POST request is sent to /user/createWithArray
    Then The response status code should be 200
    And The response should have a message with "ok"

  Scenario: Validates creates user endpoint
    Given The user wants to creates an user
    When A POST request is sent to /user
    Then The response status code should be 200
    And The response should have a message with userid
