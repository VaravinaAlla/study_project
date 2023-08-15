package dto;

import com.google.gson.Gson;
import helpers.SetupFunctions;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class RandomTestData {
    String status;
    Integer courierId;
    String customerName;
    String customerPhone;
    String comment;
    int id;
    static String token;

    public RandomTestData() {
    }
    public RandomTestData (Integer courierId, String customerName, String customerPhone, String comment, int id) {
        this.status = "OPEN";
        this.courierId = courierId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.comment = comment;
        this.id = id;
    }

    public RandomTestData createOrderApiAndReturnReceiveOrder() {
        int id = generatedRandomId();
        String customerName = generatedRandomName();
        String customerPhone = generatedRandomPhone();
        String comment = generatedRandomComment();
        SetupFunctions setupFunctions = new SetupFunctions();
        token = setupFunctions.getToken();
        RestAssured.baseURI = setupFunctions.getBaseUrl();
        RandomTestData randomOrder;
        randomOrder = new RandomTestData(0,customerName, customerPhone, comment, id);
        Gson gson = new Gson();
        Response response = given()
                .when()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .body(gson.toJson(randomOrder))
                .post("/orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
        RandomTestData receiveOrder = gson.fromJson(response.asString(), RandomTestData.class);
        return receiveOrder;
    }

    public int generatedRandomId() {
        return Integer.parseInt(RandomStringUtils.random(4,false,true));
    }

    public String generatedRandomName() {
        return RandomStringUtils.random(10,true,false);
    }

    public String generatedRandomPhone() {
        return RandomStringUtils.random(10,false,true);
    }

    public String generatedRandomComment() {
        return RandomStringUtils.random(8,true,true);
    }

    public int getId() {
        return id;
    }
    public Integer getCourierId() {
        return courierId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getCustomerPhone() {
        return customerPhone;
    }
    public String getStatus() {
        return status;
    }

    @Step("Generate random Login")
    public String generatedRandomLogin() {
        return RandomStringUtils.random(12,true,false);
    }
    @Step ("Generate random Password")
    public String generatedRandomPassword() {
        return RandomStringUtils.random(8,true,true);
    }
}