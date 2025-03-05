package services;

import POJOs.Order;
import POJOs.Pet;
import POJOs.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UserService extends BaseService{

    public Response postListOfUsers(List<User> usersArray) throws IOException {
        return sendRequest(Method.POST, "/user/createWithList", null, null, null, null, usersArray, ContentType.JSON);
    }

    public Response getUserByUsername(String username) throws JsonProcessingException {
        return sendRequest(Method.GET, "/user/{username}", Map.of("username", username), null, null, null, null, null);
    }

    public Response putUser(User userInformation, String username) throws IOException {
        return sendRequest(Method.PUT, "/user/{username}", Map.of("username", username), null, null, null, userInformation, ContentType.JSON);
    }

    public Response deleteUserByUsername(String username) throws JsonProcessingException {
        return sendRequest(Method.DELETE, "/user/{username}", Map.of("username", username), null, null, null, null, null);
    }

    public Response getUserLogin(String username, String password) throws JsonProcessingException {
        return sendRequest(Method.GET, "/user/login",null, Map.of("username", username, "password", password), null, null, null, null);
    }

    public Response postArrayOfUsers(User[] usersArray) throws IOException {
        return sendRequest(Method.POST, "/user/createWithArray", null, null, null, null, usersArray, ContentType.JSON);
    }

    public Response postUser(User usersArray) throws IOException {
        return sendRequest(Method.POST, "/user", null, null, null, null, usersArray, ContentType.JSON);
    }
}
