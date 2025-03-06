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

  Scenario Outline: Validates order by id endpoint
    Given The user wants to search the order id "<orderId>"
    When A GET request is sent to /store/order/{orderId}
    Then The response status code should be 200
    And The response should have the id "<orderId>"
    Examples:
    Examples:
      | orderId |
      | 5       |
      | 10      |

  Scenario Outline: Validates delete an order by id endpoint
    Given The user wants to delete the order with id "<orderId>"
    When A DELETE request is sent to /store/order/{orderId}
    Then The response status code should be 200
    And The response should have a message with "<orderId>"
    Examples:
      | orderId |
      | 2       |
      | 8       |