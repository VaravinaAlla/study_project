package io.qameta.junit5;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import pages.LoginPage;

import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class WebRemoteTest {
    static String baseUrl;
    static String selenoidHubUrl = "http://35.238.172.233:4444";

    @BeforeAll
    public static void setUpAll(){

        baseUrl = "http://51.250.6.164:3000/signin";

        Configuration.remote = selenoidHubUrl + "/wd/hub";

        boolean isSelenoidHubReachable = checkSelenoidHubConnectivity();
        Assumptions.assumeTrue( isSelenoidHubReachable , "Selenoid can't be connected");

        Configuration.browser = "chrome"; // "firefox";
        Configuration.browserSize = "1920x1080";
        Configuration.browserVersion = "114.0";

        Configuration.browserCapabilities.setCapability("selenoid:options",
                Map.<String, Object>of(
                        "enableVNC", true,
                        "enableVideo", true
                ));
    }

    @BeforeEach
    public void setUp(){
        System.out.println("Trying to open browsers");
        open( baseUrl );
        System.out.println("browser Opened OK");
    }

    @AfterEach
    public void tearDown(){
        closeWebDriver();
    }

    @Test
    public void checkErrorModalWindowsIsAppeared() {
        LoginPage loginPage = new LoginPage();
        loginPage.locateUserNameAndInsertText("Masha");
        loginPage.locatePasswordAndInsertText("password123");
        loginPage.clickSignIn();
        $(By.xpath("//div[@data-name='authorizationError-popup']")).shouldBe(Condition.exist, Condition.visible);
    }
    @Test
    public void checkSignInButtonIsDisabledWithEmptyLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.locateUserNameAndInsertText("");
        loginPage.locatePasswordAndInsertText("password456");
        $(By.xpath("//button[@data-name='signIn-button']")).shouldBe(Condition.disabled);
    }

    @Test
    public void checkErrorMessageIsAppearedWithMinLengthLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.locateUserNameAndInsertText("L");
        loginPage.locatePasswordAndInsertText("pass568@");
        $(By.xpath("//*[@data-name = 'username-input']/..//*[@data-name = 'username-input-error']"))
                .shouldBe(Condition.visible);
        $(By.xpath("//button[@data-name='signIn-button']")).shouldBe(Condition.disabled);
    }
    private static boolean checkSelenoidHubConnectivity() {

        try {
            int statusCode = RestAssured.given()
                    .baseUri(selenoidHubUrl)
                    .when()
                    .log()
                    .all()
                    .get()
                    .getStatusCode();

            return statusCode == HttpStatus.SC_OK;

        } catch (Exception e) {
            return false;
        }

    }
}
