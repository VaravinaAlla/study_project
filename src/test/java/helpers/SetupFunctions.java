package helpers;

import com.google.gson.Gson;
import dto.CredentialsDto;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class SetupFunctions {
    private String baseUrl;
    private  String baseUrlWeb;
    private String username;
    private String password;
    private String dbhost;
    private String dbport;
    private String dbname;
    private String dbusername;
    private String dbpassword;
    public SetupFunctions() {

        try (InputStream input = new FileInputStream("settings.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            baseUrl = properties.getProperty("baseUrl");
            baseUrlWeb = properties.getProperty("baseUrlWeb");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            dbhost = properties.getProperty("dbhost");
            dbport = properties.getProperty("dbport");
            dbname = properties.getProperty("dbname");
            dbusername = properties.getProperty("dbusername");
            dbpassword = properties.getProperty("dbpassword");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBaseUrl (){
        return baseUrl;
    }

    public String getBaseUrlWeb() {
        return baseUrlWeb;
    }

    public String getToken(){
        CredentialsDto credentialsDto = new CredentialsDto();
        credentialsDto.setUsername(username);
        credentialsDto.setPassword(password);
        Gson gson = new Gson();
        String token = given()
                .when()
                .log()
                .all()
                .header("Content-Type", "application/json")
                .body(gson.toJson(credentialsDto))
                .post(baseUrl + "/login/student")
                .then()
                .log()
                .all()
                .extract()
                .asString();
        return token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbhost() {
        return dbhost;
    }

    public String getDbport() {
        return dbport;
    }

    public String getDbname() {
        return dbname;
    }

    public String getDbusername() {
        return dbusername;
    }

    public String getDbpassword() {
        return dbpassword;
    }
}
