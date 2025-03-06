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

  Scenario Outline: Validates searching for a deleted pet
    Given The user wants to search an specific id "<petId>"
    When A GET request is sent to /pet/{petId}
    Then The response status code should be 404
    And The response should have a message with "Pet not found"
    Examples:
      | petId |
      | 000  |

  Scenario Outline: Validates pet id endpoint
    Given The user wants to search an specific id "<petId>"
    When A GET request is sent to /pet/{petId}
    Then The response status code should be 200
    And The response should have the id "<petId>"
    Examples:
      | petId |
      | 1230  |

  Scenario: Validates upload pet image endpoint
    Given The user wants to upload an image from "src/main/resources/dog.jpg" into a pet id "1230"
    When A POST request is sent to /pet/{petId}/uploadImage
    Then The response status code should be 200
    And The response should confirm the image is uploaded

  Scenario Outline: Validates creates pet endpoint
    Given The user wants to create a pet with an image from "<imagePath>" and name "<petName>"
    When A POST request is sent to /pet
    Then The response status code should be 200
    And The response should contain the pet created with an image from "<imagePath>" and name "<petName>"
    Examples:
      | imagePath                  | petName |
      | src/main/resources/dog.jpg | Lucas   |

  Scenario Outline: Validates update an existing pet endpoint
    Given The user wants to update a pet assigned to the id "<petId>" with imagePath "<imagePath>", name "<petName>" and status "<status>"
    When A PUT request is sent to /pet
    Then The response status code should be 200
    And The response should contain the pet updated with imagePath "<imagePath>", name "<petName>" and status "<status>"
    Examples:
      | petId | imagePath                  | petName | status  |
      | 1230  | src/main/resources/dog.jpg | sam     | pending |

  Scenario Outline: Validates update a pet in the store with form data
    Given The user wants to update a pet with id "<petId>" with name "<newName>" and status "<newStatus>"
    When A POST request is sent to /pet/{petId}
    Then The response status code should be 200
    And The response should have a message with "<petId>"
    Examples:
      | petId | newName  | newStatus |
      | 1230  | Luquitas | available |

  Scenario Outline: Validates delete a pet endpoint
    Given The user wants to delete the pet with id "<petId>"
    When A DELETE request is sent to /pet/{petId}
    Then The response status code should be 200
    And The response should have a message with "<petId>"
    Examples:
      | petId |
      | 1230  |

  Scenario Outline: Validates update a pet in the store with wrong data
    Given The user wants to update a pet with id "<petId>" with name "<newName>" and status "<newStatus>"
    When A POST request is sent to /pet/{petId}
    Then The response status code should be 404
    And The response should have a message with "not found"
    Examples:
      | petId | newName  | newStatus |
      | 1230  | Luquitas | available |

