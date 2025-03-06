package services;

import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import POJOs.Order;

import java.util.Map;

public class StoreService extends BaseService {

    public Response getStoreInventory() {
        logger.info("Sending request to: /store/inventory");
        return sendRequest(Method.GET, "/store/inventory", null, null, null, null, null, null);
    }

    public Response postOrder(Order orderInformation) {
        logger.info("Sending request to: /store/order");
        return sendRequest(Method.POST, "/store/order", null, null, null, null, orderInformation, ContentType.JSON);
    }

    public Response getOrderById(String orderId) {
        logger.info("Sending request to: /store/order"+orderId);
        return sendRequest(Method.GET, "/store/order/{orderId}", Map.of("orderId", orderId), null, null, null, null, null);
    }

    public Response deleteOrderById(String orderId) {
        logger.info("Sending request to: /store/order"+orderId);
        return sendRequest(Method.DELETE, "/store/order/{orderId}", Map.of("orderId", orderId), null, null, null, null, null);
    }
}
