Feature: Pet API Validations

  Scenario Outline: Validate pets status endpoint values and status code 200
    Given The user wants to consult the pets with the following "<petStatus>"
    When A GET request is sent to /pet/findByStatus
    Then The response status code should be 200
    And The response should contain pets with status "<petStatus>"
    Examples:
      | petStatus |
      | available |
      | pending   |
      | sold      |

  Scenario: Validates pet id endpoint
    Given The user wants to search an specific id "1230"
    When A GET request is sent to /pet/{petId}
    Then The response status code should be 200
    And The response should have the id "1230"


  Scenario: Validates upload pet image endpoint
    Given The user wants to upload an image from "src/main/resources/dogImage.jpg" into a pet id "1230"
    When A POST request is sent to /pet/{petId}/uploadImage
    Then The response status code should be 200
    And The response should confirm the image is uploaded

  Scenario: Validates creates pet endpoint
    Given The user wants to create a pet with an image from "https://humanidades.com/wp-content/uploads/2017/02/perro-1-e1561678907722.jpg" and name "Lucas"
    When A POST request is sent to /pet
    Then The response status code should be 200
    And The response should contain the pet created with an image from "https://humanidades.com/wp-content/uploads/2017/02/perro-1-e1561678907722.jpg" and name "Lucas"

  Scenario: Validates update an existing pet endpoint
    Given The user wants to update a pet assigned to the id "1230" with imagePath "https://es.mypet.com/wp-content/uploads/sites/23/2021/03/GettyImages-1143107320-e1597136744606.jpg", name "sam" and status "pending"
    When A PUT request is sent to /pet
    Then The response status code should be 200
    And The response should contain the pet updated with imagePath "https://es.mypet.com/wp-content/uploads/sites/23/2021/03/GettyImages-1143107320-e1597136744606.jpg", name "sam" and status "pending"

  Scenario: Validates update a pet in the store with form data endpoint
    Given The user wants to update a pet with id "1230" with name "Luquitas" and status "available"
    When A POST request is sent to /pet/{petId}
    Then The response status code should be 200
    And The response should have a message with "1230"

  Scenario: Validates delete a pet endpoint
    Given The user wants to delete the pet with id "1230"
    When A DELETE request is sent to /pet/{petId}
    Then The response status code should be 200
    And The response should have a message with "1230"