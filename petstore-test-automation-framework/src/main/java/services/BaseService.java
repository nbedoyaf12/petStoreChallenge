package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class BaseService {

    protected static final String BASE_URL = "https://petstore.swagger.io/v2";

    protected static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setAccept(ContentType.JSON)
                .build();
    }

    protected Response sendRequest(Method method,
                                   String endpoint,
                                   Map<String, ?> pathParams,
                                   Map<String, ?> queryParams,
                                   File file,
                                   String additionalMetadata,
                                   Object body,
                                   ContentType contentType) throws JsonProcessingException {
        RequestSpecification requestSpecification = getRequestSpec();
        if (pathParams != null) {
            requestSpecification.pathParams(pathParams);
        }
        if (queryParams != null) {
            requestSpecification.queryParams(queryParams);
        }
        if (file != null) {
            requestSpecification.multiPart("file", file);
        }
        if (additionalMetadata != null) {
            requestSpecification.multiPart("additionalMetadata", additionalMetadata);
        }
        if (body != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(body);
            requestSpecification.body(json);
        }
        if (contentType != null) {
            requestSpecification.contentType(contentType);
        }

        return given().spec(requestSpecification).when().request(method, endpoint);
    }

}
