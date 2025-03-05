package stepDefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import POJOs.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import services.PetService;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetStepDefinitions {
    private static final Logger logger = LoggerFactory.getLogger(PetStepDefinitions.class);

    private PetService petService = new PetService();
    private String petStatus;
    private String getPetId;
    private File imageFile;
    private Pet pet;
    private int randomPetId;
    private String petName;

    private final TestContext testContext;

    public PetStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }
    @Given("The user wants to consult the pets with the following {string}")
    public void theUserWantsToConsultThePetsWithTheFollowing(String petStatus) {
        this.petStatus = petStatus.toLowerCase();
    }

    @When("A GET request is sent to \\/pet\\/findByStatus")
    public void aGETRequestIsSentToPetFindByStatus() throws JsonProcessingException {
        Response response = petService.getPetsStatus(this.petStatus);
        testContext.setResponse(response);
    }

    @Then("The response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        logger.info(testContext.getResponse().getBody().prettyPrint());
        Assert.assertEquals(testContext.getResponse().statusCode(), statusCode);
    }

    @And("The response should contain pets with status {string}")
    public void theResponseShouldContainPetsWithStatus(String expectedStatus) {
        List<Map<String, Object>> pets = testContext.getResponse().jsonPath().getList("$");

        Assert.assertFalse(pets.isEmpty(), "Response should not be empty");
        for (Map<String, Object> pet : pets) {
            Assert.assertEquals(pet.get("status"), expectedStatus, "Pet status is not correct");
        }
    }

    @Given("The user wants to search an specific id {string}")
    public void theUserWantsToSearchAnSpecificId(String getPetId) {
        this.getPetId = getPetId;
        logger.info(this.getPetId);
    }

    @When("A GET request is sent to \\/pet\\/\\{petId}")
    public void aGETRequestIsSentToPetPetId() throws JsonProcessingException {
        logger.info(this.getPetId);
        Response response = petService.getPetId(this.getPetId);
        testContext.setResponse(response);
    }

    @And("The response should have the id {string}")
    public void theResponseShouldHaveTheId(String expectedPetId) {
        Assert.assertEquals(testContext.getResponse().getBody().jsonPath().get("id").toString(), expectedPetId, "Pet id is not correct");
    }

    @Given("The user wants to upload an image from {string} into a pet id {string}")
    public void theUserWantsToUploadAnImageFromIntoAPetId(String imagePath, String petId) {
        this.getPetId = petId;
        this.imageFile = new File(imagePath);
    }

    @And("The response should confirm the image is uploaded")
    public void theResponseShouldConfirmTheImageIsUploaded() {
        Assert.assertTrue(petService.isImageUploaded(testContext.getResponse()), "The image is not uploaded correctly");
    }

    @When("A POST request is sent to \\/pet\\/\\{petId}\\/uploadImage")
    public void aPOSTRequestIsSentToPetPetIdUploadImage() throws JsonProcessingException {
        Response response = petService.postUploadImage(this.getPetId, this.imageFile, "");
        testContext.setResponse(response);
    }

    @Given("The user wants to create a pet with an image from {string} and name {string}")
    public void theUserWantsToCreateAPetWithAnImageFromAndName(String imagePath, String petName) {
        this.randomPetId = PetService.createRandomPetId();
        Map<String, Object> category = Map.of("id", 0, "name", "string");
        List<Map<String, Object>> tags = List.of(Map.of("id", 0, "name", "Friendly"));
        this.pet = new Pet(
                randomPetId,
                category,
                petName,
                List.of(imagePath),
                tags,
                "available"
        );
    }

    @When("A POST request is sent to \\/pet")
    public void aPOSTRequestIsSentToPet() throws IOException {
        Response response = petService.postPet(this.pet);
        testContext.setResponse(response);
    }

    @And("The response should contain the pet created with an image from {string} and name {string}")
    public void theResponseShouldContainThePetCreatedWithAnImageFromAndName(String imagePath, String petName) {
        Map<String, Object> petResponse = testContext.getResponse().jsonPath().getMap("$");

        int responsePetId = (int) petResponse.get("id");
        String responsePetName = (String) petResponse.get("name");
        Assert.assertEquals(responsePetId, this.randomPetId, "The petId is not correct");
        Assert.assertEquals(responsePetName, petName, "The pet name is not correct");

        List<String> photoUrlsList = (List<String>) petResponse.get("photoUrls");
        Assert.assertFalse(photoUrlsList.isEmpty(), "The photoUrls list is empty");
        Assert.assertEquals(photoUrlsList.get(0), imagePath, "The pet image path is not correct");
    }

    @When("A PUT request is sent to \\/pet")
    public void aPUTRequestIsSentToPet() throws IOException {
        Response response = petService.putPet( this.pet);
        testContext.setResponse(response);
    }

    @Given("The user wants to update a pet assigned to the id {string} with imagePath {string}, name {string} and status {string}")
    public void theUserWantsToUpdateAPetAssignedToTheIdWithImagePathNameAndStatus(String petId, String imagePath, String newName, String newStatus) {
        Map<String, Object> category = Map.of("id", 0, "name", "string");
        List<Map<String, Object>> tags = List.of(Map.of("id", 0, "name", "Friendly"));
        this.pet = new Pet(
                Integer.parseInt(petId),
                category,
                newName,
                List.of(imagePath),
                tags,
                newStatus
        );
    }

    @And("The response should contains the pet updated with imagePath {string}, name {string} and status {string}")
    public void theResponseShouldContainsThePetUpdatedWithImagePathNameAndStatus(String imagePath, String petName, String petStatus) {
        Map<String, Object> petResponse = testContext.getResponse().jsonPath().getMap("$");

        String responsePetName = (String) petResponse.get("name");
        String responsePetStatus = (String) petResponse.get("status");
        Assert.assertEquals(responsePetName, petName, "The pet name is not correct");
        Assert.assertEquals(responsePetStatus, petStatus, "The pet status is not correct");

        List<String> photoUrlsList = (List<String>) petResponse.get("photoUrls");
        Assert.assertFalse(photoUrlsList.isEmpty(), "The photoUrls list is empty");
        Assert.assertEquals(photoUrlsList.get(0), imagePath, "The pet image path is not correct");
    }

    @Given("The user wants to update a pet with id {string} with name {string} and status {string}")
    public void theUserWantsToUpdateAPetWithIdWithNameAndStatus(String petId, String petName, String petStatus) {
        this.getPetId= petId;
        this.petName= petName;
        this.petStatus= petStatus;
    }

    @When("A POST request is sent to \\/pet\\/\\{petId}")
    public void aPOSTRequestIsSentToPetPetId() throws JsonProcessingException {
        Map<String, Object> formData = new HashMap<>();
        formData.put("name", this.petName);
        formData.put("status", this.petStatus);
        Response response = petService.updatePostPet(this.getPetId, formData);
        testContext.setResponse(response);
    }

    @And("The response should have a message with {string}")
    public void theResponseShouldHaveAMessageWith(String messageValue) {
        Assert.assertTrue(this.testContext.getResponse().jsonPath().getString("message").contains(messageValue), "The message doesn't contain the message wanted");
    }

    @Given("The user wants to delete the pet with id {string}")
    public void theUserWantsToDeleteThePetWithId(String petId) {
        this.getPetId=petId;
    }

    @When("A DELETE request is sent to \\/pet\\/\\{petId}")
    public void aDELETERequestIsSentToPetPetId() throws JsonProcessingException {
        Response response = petService.deletePet( this.getPetId);
        testContext.setResponse(response);
    }
}
