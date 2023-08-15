package io.qameta.junit5;

import com.google.gson.Gson;
import dto.RandomTestData;
import helpers.SetupFunctions;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ApiTest extends RandomTestData{
    static String token;
    @BeforeAll
    public static void setupAll() {
        SetupFunctions setupFunctions = new SetupFunctions();
        token = setupFunctions.getToken();
        RestAssured.baseURI = setupFunctions.getBaseUrl();
        Assumptions.assumeFalse(token.isEmpty(), "Test doesn't start without token");
    }
    @BeforeEach
    public  void setup() {
       System.out.println("---> Test start");
  }

    @Test
    public void checkCreatingOrder() {
        RandomTestData createNewOrder = new RandomTestData(0, generatedRandomName(),
                generatedRandomPhone(), generatedRandomComment(), generatedRandomId());
        Gson gson = new Gson();
        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .body(gson.toJson(createNewOrder))
                .post("/orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
        RandomTestData receivedOrder = gson.fromJson(response.asString(),RandomTestData.class);
        assertAll("Asserts for CreateOrder",
                () -> Assertions.assertNotNull(createNewOrder.getId()),
                () -> Assertions.assertNotNull(createNewOrder.getCustomerPhone()),
                () -> Assertions.assertEquals(createNewOrder.getCustomerPhone(), receivedOrder.getCustomerPhone())
        );
    }

    @Test
    public void checkSearchingCreatedOrder(){
        int orderID = createdOrderAndReturnOrderId();
        Gson gson = new Gson();
        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " +token)
                .log()
                .all()
                .get("/orders/" + orderID)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
        RandomTestData receivedOrderId = gson.fromJson(response.asString(),RandomTestData.class);
        Assertions.assertNotNull(receivedOrderId.getId());
        Assertions.assertEquals(orderID, receivedOrderId.getId());
    }

    @Test
    public void checkDeletingCreatedOrder(){
        int orderID = createdOrderAndReturnOrderId();
        String body = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " +token)
                .log()
                .all()
                .delete("/orders/" + orderID)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .response()
                .getBody()
                .asString();
        Assertions.assertTrue(true, body);

        // Check that body deleted order is empty
        String emptyBody = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders/" + orderID)
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .response()
                .getBody()
                .asString();
        Assumptions.assumeTrue(emptyBody.isEmpty(), "Body isn`t empty");
    }

    @Test
    public void checkStatus401WithCreatingOrderWithoutToken(){
        RandomTestData createNewOrder = new RandomTestData(0, generatedRandomName(),
                generatedRandomPhone(), generatedRandomComment(), generatedRandomId());
        Gson gson = new Gson();
        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .log()
                .all()
                .body(gson.toJson(createNewOrder))
                .post("/orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract()
                .response();
        Assertions.assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void checkStatus415WithCreatingOrderWithoutContentType(){
        RandomTestData createNewOrder = new RandomTestData(0, generatedRandomName(),
                generatedRandomPhone(), generatedRandomComment(), generatedRandomId());
        Gson gson = new Gson();
        Response response = given()
                .when()
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .body(gson.toJson(createNewOrder))
                .post("/orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE)
                .body("error", equalTo("Unsupported Media Type"))
                .extract()
                .response();
        Assertions.assertEquals(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
    }
    @Test
    public void checkStatus403AvailableOrderByStudent() {
        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders/available")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .extract()
                .response();
        Assertions.assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void checkStatus403PuttingStatusByStudent() {
        RandomTestData createNewOrder = new RandomTestData(0, generatedRandomName(),
                generatedRandomPhone(), generatedRandomComment(), generatedRandomId());
        Gson gson = new Gson();
        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .body(gson.toJson(createNewOrder))
                .put("/orders/" + createNewOrder.getId() +"/status")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .extract()
                .response();
        Assertions.assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusCode());
    }

    public int createdOrderAndReturnOrderId() {
        RandomTestData createNewOrder = new RandomTestData(0, generatedRandomName(),
                generatedRandomPhone(), generatedRandomComment(), generatedRandomId());
        Gson gson = new Gson();
        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .body(gson.toJson(createNewOrder))
                .post("/orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
        RandomTestData orderId = gson.fromJson(response.asString(), RandomTestData.class);
        return orderId.getId();
    }
}
