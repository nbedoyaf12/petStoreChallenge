package services;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import POJOs.Pet;

import java.io.File;
import java.util.Map;
import java.util.Random;

public class PetService extends BaseService {

    public Response getPetsStatus(String status) {
        logger.info("Sending request to: /pet/findByStatus");
        return sendRequest(Method.GET, "/pet/findByStatus", null, Map.of("status", status), null, null, null, null);
    }

    public Response getPetId(String petId) {
        logger.info("Sending request to: /pet/"+petId);
        return sendRequest(Method.GET, "/pet/{petId}", Map.of("petId", petId), null, null, null, null, null);
    }

    public Response postUploadImage(String petId, File imageFile, String additionalMetadata) {
        logger.info("Sending request to: /pet/"+petId+"/uploadImage");
        return sendRequest(Method.POST, "/pet/{petId}/uploadImage", Map.of("petId", petId), null, imageFile, additionalMetadata, null, ContentType.MULTIPART);
    }

    public Response postPet(Pet petInformation) {
        logger.info("Sending request to: /pet/");
        return sendRequest(Method.POST, "/pet", null, null, null, null, petInformation, ContentType.JSON);
    }

    public Response putPet(Pet petInformation) {
        logger.info("Sending request to: /pet/");
        return sendRequest(Method.PUT, "/pet", null, null, null, null, petInformation, ContentType.JSON);
    }

    public Response updatePostPet(String petId, Object body) {
        logger.info("Sending request to: /pet/"+petId);
        return sendRequest(Method.POST, "/pet/{petId}", Map.of("petId", petId), null, null, null, body, ContentType.URLENC);
    }

    public Response deletePet(String petId) {
        logger.info("Sending request to: /pet/"+petId);
        return sendRequest(Method.DELETE, "/pet/{petId}", Map.of("petId", petId), null, null, null, null, null);
    }

    public boolean isImageUploaded(Response response) {
        try {
            String message = response.jsonPath().getString("message");
            return message.contains("uploaded");
        } catch (Exception e) {
            return false;
        }
    }

    public static int createRandomPetId() {
        Random random = new Random();
        return 100_000 + random.nextInt(9_000_000);
    }
}