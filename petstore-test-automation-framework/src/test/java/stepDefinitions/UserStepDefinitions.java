package stepDefinitions;

import POJOs.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import services.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserStepDefinitions {
    private static final Logger logger = LoggerFactory.getLogger(PetStepDefinitions.class);

    private final TestContext testContext;
    private List<User> usersList;
    private User[] usersArray;
    private UserService userService = new UserService();
    private String username;
    private User user;
    private String password;

    public UserStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("The user wants to creates a list of {string} users")
    public void theUserWantsToCreatesAListOfUsers(String usersNumber) {
        this.usersList = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(usersNumber); i++) {
            usersList.add(new User(i, "name" + i, "firstName" + i, "lastName" + i, "email" + i + "@mail.com", "123" + i, "100200" + i, i));
            logger.info(String.valueOf(usersList.get(i).getId()));
        }
    }

    @When("A POST request is sent to \\/user\\/createWithList")
    public void aPOSTRequestIsSentToUserCreateWithList() throws IOException {
        Response response = userService.postListOfUsers(this.usersList);
        testContext.setResponse(response);
    }

    @Given("The user wants to search the username {string}")
    public void theUserWantsToSearchTheUsername(String username) {
        this.username = username;
    }

    @When("A GET request is sent to \\/user\\/\\{username}")
    public void aGETRequestIsSentToUserUsername() throws JsonProcessingException {
        Response response = userService.getUserByUsername(this.username);
        testContext.setResponse(response);
    }

    @And("The response should have the username {string}")
    public void theResponseShouldHaveTheUsername(String username) {
        Assert.assertEquals(testContext.getResponse().getBody().jsonPath().get("username").toString(), username, "Username is not correct");
    }

    @Given("The user wants to update an user with the name {string}")
    public void theUserWantsToUpdateAnUserWithTheName(String userName) {
        this.username = userName;
        this.user = new User(0, "user2", "firstName2", "lastName2", "email2@mail.com", "pass2", "300000002", 2);
    }

    @When("A PUT request is sent to \\/user\\/\\{username}")
    public void aPUTRequestIsSentToUserUsername() throws IOException {
        Response response = userService.putUser(this.user, this.username);
        testContext.setResponse(response);
    }

    @And("The response should contains the user updated")
    public void theResponseShouldContainsTheUserUpdated() {
        Response response = testContext.getResponse();
        Assert.assertEquals(this.user.getId(), response.jsonPath().getLong("id"), "The id is not correct");
        Assert.assertEquals(this.user.getUsername(), response.jsonPath().get("username"), "The username is not correct");
        Assert.assertEquals(this.user.getFirstName(), response.jsonPath().get("firstName"), "The firstName is not correct");
        Assert.assertEquals(this.user.getLastName(), response.jsonPath().get("lastName"), "The lastName is not correct");
        Assert.assertEquals(this.user.getEmail(), response.jsonPath().get("email"), "The email is not correct");
        Assert.assertEquals(this.user.getPassword(), response.jsonPath().get("password"), "The password is not correct");
        Assert.assertEquals(this.user.getPhone(), response.jsonPath().get("phone"), "The phone is not correct");
        Assert.assertEquals(this.user.getUserStatus(), response.jsonPath().getInt("userStatus"), "The userStatus is not correct");
    }

    @And("The response should have a message with userid")
    public void theResponseShouldHaveAMessageWithUserid() {
        Assert.assertEquals(this.testContext.getResponse().jsonPath().getString("message"), String.valueOf(user.getId()), "The message doesn't contain the userId");
    }

    @Given("The user wants to delete the user with name {string}")
    public void theUserWantsToDeleteTheUserWithName(String username) {
        this.username = username;
    }

    @When("A DELETE request is sent to \\/user\\/\\{username}")
    public void aDELETERequestIsSentToUserUsername() throws JsonProcessingException {
        Response response = userService.deleteUserByUsername(this.username);
        testContext.setResponse(response);
    }

    @Given("The user wants to logs user into the system with the username {string} and password {string}")
    public void theUserWantsToLogsUserIntoTheSystemWithTheUsernameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @When("A GET request is sent to \\/user\\/login")
    public void aGETRequestIsSentToUserLogin() throws JsonProcessingException {
        Response response = userService.getUserLogin(this.username, this.password);
        testContext.setResponse(response);
    }

    @Given("The user wants to creates an array of {string} users")
    public void theUserWantsToCreatesAnArrayOfUsers(String usersNumber) {
        int usersNumberArray = Integer.parseInt(usersNumber);
        this.usersArray = new User[usersNumberArray];
        for (int i = 0; i < usersNumberArray; i++) {
            usersArray[i] = new User(i, "name" + i, "firstName" + i, "lastName" + i, "email" + i + "@mail.com", "123" + i, "100200" + i, i);
        }
    }

    @When("A POST request is sent to \\/user\\/createWithArray")
    public void aPOSTRequestIsSentToUserCreateWithArray() throws IOException {
        Response response = userService.postArrayOfUsers(this.usersArray);
        testContext.setResponse(response);
    }

    @When("A POST request is sent to \\/user")
    public void aPOSTRequestIsSentToUser() throws IOException {
        Response response = userService.postUser(this.user);
        testContext.setResponse(response);
    }

    @Given("The user wants to creates an user")
    public void theUserWantsToCreatesAnUser() {
        this.user= new User(0, "user2", "firstName2", "lastName2", "email2@mail.com", "pass2", "300000002", 2);
    }
}
