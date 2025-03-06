package services;

import POJOs.User;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class UserService extends BaseService {

    public Response postListOfUsers(List<User> usersArray) {
        logger.info("Sending request to: /user/createWithList");
        return sendRequest(Method.POST, "/user/createWithList", null, null, null, null, usersArray, ContentType.JSON);
    }

    public Response getUserByUsername(String username) {
        logger.info("Sending request to: /user/" + username);
        return sendRequest(Method.GET, "/user/{username}", Map.of("username", username), null, null, null, null, null);
    }

    public Response putUser(User userInformation, String username) {
        logger.info("Sending request to: /user/" + username);
        return sendRequest(Method.PUT, "/user/{username}", Map.of("username", username), null, null, null, userInformation, ContentType.JSON);
    }

    public Response deleteUserByUsername(String username) {
        logger.info("Sending request to: /user/" + username);
        return sendRequest(Method.DELETE, "/user/{username}", Map.of("username", username), null, null, null, null, null);
    }

    public Response getUserLogin(String username, String password) {
        logger.info("Sending request to: /user/login");
        return sendRequest(Method.GET, "/user/login", null, Map.of("username", username, "password", password), null, null, null, null);
    }

    public Response postArrayOfUsers(User[] usersArray) {
        logger.info("Sending request to: /user/createWithArray");
        return sendRequest(Method.POST, "/user/createWithArray", null, null, null, null, usersArray, ContentType.JSON);
    }

    public Response postUser(User usersArray) {
        logger.info("Sending request to: /user");
        return sendRequest(Method.POST, "/user", null, null, null, null, usersArray, ContentType.JSON);
    }
}
