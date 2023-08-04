package io.qameta.junit5;

import com.google.gson.Gson;
import dto.RandomOrder;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ApiRemoteTest {
    @BeforeEach
    public  void setup() {
        System.out.println("---> Test start");
        RestAssured.baseURI = "http://51.250.6.164:8080/";
    }

    @Test
    public void createOrderAndCheckStatusCodePost(){
        RandomOrder randomOrder = new RandomOrder();
        Gson gson = new Gson();
        randomOrder.setId(generatedRandomId());
        randomOrder.setCustomerName(generatedRandomName());
        randomOrder.setCustomerPhone(generatedRandomPhone());
        randomOrder.setComment(generatedRandomComment());

        given()
                .when()
                .log()
                .all()
                .header("Content-Type", "application/json")
                .body(gson.toJson(randomOrder))
                .post("test-orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK);
    }
    @Test  //homework11
    public void createOrderWithoutHeaderAndCheckStatusCodePost(){
        RandomOrder randomOrder = new RandomOrder();
        Gson gson = new Gson();
        randomOrder.setCustomerName(generatedRandomName());
        randomOrder.setCustomerPhone(generatedRandomPhone());
        randomOrder.setComment(generatedRandomComment());
        given()
                .when()
                .body(gson.toJson(randomOrder))
                .log()
                .all()
                .post("test-orders")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }
    @Test
    public void checkResponseBodyIsOpenGet() {
        String status = given()
                .when()
                .log()
                .all()
                .get("test-orders/8")
                .then()
                .log()
                .all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .path("status");
        Assertions.assertEquals("OPEN", status);
    }

    public String generatedRandomName() {
        int lengthName = 10;
        boolean useLettersName = true;
        boolean useSymbolsName = false;
        String generatedStringName = RandomStringUtils.random(lengthName, useLettersName, useSymbolsName);
        return generatedStringName;
    }

    public int generatedRandomId() {
        int lengthId = 4;
        boolean useLettersId = false;
        boolean useSymbolsId = true;
        String generatedStringId = RandomStringUtils.random(lengthId, useLettersId, useSymbolsId);
        return Integer.parseInt(generatedStringId);
    }
    public String generatedRandomPhone() {
        int lengthPhone = 10;
        boolean useLettersPhone = false;
        boolean useNumberPhone = true;
        String generatedStringPhone = RandomStringUtils.random(lengthPhone, useLettersPhone, useNumberPhone);
        return generatedStringPhone;
    }
    public String generatedRandomComment() {
        int lengthComment = 8;
        boolean useLettersComment = true;
        boolean useSymbolsComment = true;
        String generatedStringComment = RandomStringUtils.random(lengthComment, useLettersComment, useSymbolsComment);
        return generatedStringComment;
    }

}
