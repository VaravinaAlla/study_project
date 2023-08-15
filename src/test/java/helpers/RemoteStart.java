package helpers;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;

public class RemoteStart {
    static String baseUrl;
    static String selenoidHubUrl = "http://35.238.172.233:4444";

    @BeforeAll
    public static void setUpAll(){

        baseUrl = new SetupFunctions().getBaseUrlWeb();

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
