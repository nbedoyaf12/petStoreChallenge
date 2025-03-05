package stepDefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import POJOs.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import services.StoreService;

import java.io.IOException;

public class StoreStepDefinitions {
    private static final Logger logger = LoggerFactory.getLogger(PetStepDefinitions.class);

    private StoreService storeService = new StoreService();
    private Order order;
    private final TestContext testContext;
    private String orderId;

    public StoreStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @When("A GET request is sent to \\/store\\/inventory")
    public void aGETRequestIsSentToStoreInventory() throws JsonProcessingException {
        Response response = storeService.getStoreInventory();
        testContext.setResponse(response);
    }

    @Given("The user wants to search the store inventory")
    public void theUserWantsToSearchTheStoreInventory() {
        logger.info("The user wants to search the store inventory");
    }

    @And("The response should have values")
    public void theResponseShouldHaveValues() {
        Assert.assertFalse(testContext.getResponse().jsonPath().getMap("$").isEmpty(), "The inventory is empty");
    }

    @Given("The user wants to place and order")
    public void theUserWantsToPlaceAndOrder() {
        this.order = new Order(
                0,
                0,
                1,
                "2025-03-05T16:02:14.780Z",
                "placed",
                true
        );
    }

    @When("A POST request is sent to \\/store\\/order")
    public void aPOSTRequestIsSentToStoreOrder() throws IOException {
        Response response = storeService.postOrder(this.order);
        testContext.setResponse(response);
    }

    @And("The response should have the order information")
    public void theResponseShouldHaveTheOrderInformation() {
        Response response = testContext.getResponse();
        Assert.assertEquals(this.order.getId(), response.jsonPath().getLong("id"), "The id is not correct");
        Assert.assertEquals(String.valueOf(this.order.getPetId()), response.jsonPath().get("petId"), "The petId is not correct");
        Assert.assertEquals(String.valueOf(this.order.getQuantity()), response.jsonPath().get("quantity"), "The quantity is not correct");
        Assert.assertEquals(this.order.getShipDate(), response.jsonPath().get("shipDate"), "The shipDate is not correct");
        Assert.assertEquals(this.order.getStatus(), response.jsonPath().get("status"), "The status is not correct");
        Assert.assertEquals(this.order.isComplete(), response.jsonPath().get("complete"), "The complete is not correct");
    }

    @Given("The user wants to search the order id {string}")
    public void theUserWantsToSearchTheOrderId(String orderId) {
        this.orderId = orderId;
    }

    @When("A GET request is sent to \\/store\\/order\\/\\{orderId}")
    public void aGETRequestIsSentToStoreOrderOrderId() throws JsonProcessingException {
        Response response = storeService.getOrderById(this.orderId);
        testContext.setResponse(response);
    }

    @Given("The user wants to delete the order with id {string}")
    public void theUserWantsToDeleteTheOrderWithId(String orderId) {
        this.orderId=orderId;
    }

    @When("A DELETE request is sent to \\/store\\/order\\/\\{orderId}")
    public void aDELETERequestIsSentToStoreOrderOrderId() throws JsonProcessingException {
        Response response = storeService.deleteOrderById(this.orderId);
        testContext.setResponse(response);
    }
}
