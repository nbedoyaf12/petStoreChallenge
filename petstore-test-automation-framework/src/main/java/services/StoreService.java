package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import POJOs.Order;

import java.io.IOException;
import java.util.Map;

public class StoreService extends BaseService{

    public Response getStoreInventory() throws JsonProcessingException {
        return sendRequest(Method.GET, "/store/inventory", null, null, null, null, null, null);
    }

    public Response postOrder(Order orderInformation) throws IOException {
        return sendRequest(Method.POST, "/store/order", null, null, null, null, orderInformation, ContentType.JSON);
    }

    public Response getOrderById(String orderId) throws JsonProcessingException {
        return sendRequest(Method.GET, "/store/order/{orderId}", Map.of("orderId", orderId), null, null, null, null, null);
    }

    public Response deleteOrderById(String orderId) throws JsonProcessingException {
        return sendRequest(Method.DELETE, "/store/order/{orderId}", Map.of("orderId", orderId), null, null, null, null, null);
    }
}
