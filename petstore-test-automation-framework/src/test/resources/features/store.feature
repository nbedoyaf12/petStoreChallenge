Feature: Store API validations

  Scenario: Validates store inventory endpoint
    Given The user wants to search the store inventory
    When A GET request is sent to /store/inventory
    Then The response status code should be 200
    And The response should have values

  Scenario: Validates place and order for a pet endpoint
    Given The user wants to place and order
    When A POST request is sent to /store/order
    Then The response status code should be 200
    And The response should have the order information

  Scenario: Validates order by id endpoint
    Given The user wants to search the order id "5"
    When A GET request is sent to /store/order/{orderId}
    Then The response status code should be 200
    And The response should have the id "5"

  Scenario: Validates delete an order by id endpoint
    Given The user wants to delete the order with id "2"
    When A DELETE request is sent to /store/order/{orderId}
    Then The response status code should be 200
    And The response should have a message with "2"
