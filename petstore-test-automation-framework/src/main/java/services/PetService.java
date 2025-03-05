package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import POJOs.Pet;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class PetService extends BaseService {

    public Response getPetsStatus(String status) throws JsonProcessingException {
//        RequestSpecification requestSpecification = getRequestSpec()
//                .param("status", status);
//        return given()
//                .spec(requestSpecification)
//                .when()
//                .get("/pet/findByStatus");

        return sendRequest(Method.GET, "/pet/findByStatus", null, Map.of("status", status), null, null, null, null);
    }

    public Response getPetId(String petId) throws JsonProcessingException {
        return sendRequest(Method.GET, "/pet/{petId}", Map.of("petId", petId), null, null, null, null, null);
    }

    public Response postUploadImage(String petId, File imageFile, String additionalMetadata) throws JsonProcessingException {
        return sendRequest(Method.POST, "/pet/{petId}/uploadImage", Map.of("petId", petId), null, imageFile, additionalMetadata, null, ContentType.MULTIPART);
    }

    public Response postPet(Pet petInformation) throws IOException {
        return sendRequest(Method.POST, "/pet", null, null, null, null, petInformation, ContentType.JSON);
    }

    public Response putPet(Pet petInformation) throws IOException {
        return sendRequest(Method.PUT, "/pet", null, null, null, null, petInformation, ContentType.JSON);
    }

    public Response updatePostPet(String petId, Object body) throws JsonProcessingException {
        return sendRequest(Method.POST, "/pet/{petId}", Map.of("petId", petId), null, null, null, null, ContentType.URLENC);
    }

    public Response deletePet(String petId) throws JsonProcessingException {
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

    public static int createRandomPetId(){
        Random random = new Random();
        return 100_000 + random.nextInt(9_000_000);
    }
}