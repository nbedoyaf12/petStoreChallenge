Feature: Store API validations

  Scenario Outline: Validates create multiple users endpoints
    Given The user wants to create "<usersNumber>" users using "<method>"
    When A POST request is sent to /user/"<method>"
    Then The response status code should be 200
    And The response should have a message with "ok"
    Examples:
      | usersNumber | method          |
      | 3           | createWithList  |
      | 2           | createWithArray |

  Scenario: Validates creates user endpoint
    Given The user wants to creates an user
    When A POST request is sent to /user
    Then The response status code should be 200
    And The response should have a message with userid

  Scenario Outline: Validates search user by name endpoint
    Given The user wants to search the username "<username>"
    When A GET request is sent to /user/{username}
    Then The response status code should be 200
    And The response should have the username "<username>"
    Examples:
      | username |
      | user1    |

  Scenario: Validates update an existing user endpoint
    Given The user wants to update an user with the name "user1"
    When A PUT request is sent to /user/{username}
    Then The response status code should be 200
    And The response should have a message with userid

  Scenario Outline: Validates delete an user by username endpoint
    Given The user wants to delete the user with name "<username>"
    When A DELETE request is sent to /user/{username}
    Then The response status code should be 200
    And The response should have a message with "<username>"
    Examples:
      | username |
      | user1    |

  Scenario: Validates login user endpoint
    Given The user wants to logs user into the system with the username "user1" and password "1230"
    When A GET request is sent to /user/login
    Then The response status code should be 200
    And The response should have a message with "logged in user session:"

