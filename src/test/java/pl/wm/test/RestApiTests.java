package pl.wm.test;


import io.restassured.config.EncoderConfig;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestApiTests {

    @Test
    public void serverShouldReturn_200() {
        given()
                .when()
                .get("https://reqres.in/api/users/3")
                .then()
                .statusCode(200);
    }

    @Test
    public void lastNameShouldBe_Wong() {
        given()
                .baseUri("https://reqres.in/api")
                .pathParam("userId", 3)
                .when()
                .get("/users/{userId}")
                .then()
                .body("data.last_name", equalTo("Wong"));
    }

    @Test
    public void verifyHeadersAreSent() {

        final String HEADER_VALUE = "someValue";

        given()
                .config(config().encoderConfig(EncoderConfig.encoderConfig()
                        .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .baseUri("https://postman-echo.com")
                .header("myheader", HEADER_VALUE)
                .when()
                .log().all()
                .post("/post")
                .then()
                .log().all()
                .statusCode(200)
                .body("headers.myheader", equalTo(HEADER_VALUE));
    }

    @Test
    public void verifyResponseContentTypeAndCharset() {


        given()
                .config(config().encoderConfig(EncoderConfig.encoderConfig()
                        .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .baseUri("https://postman-echo.com")
                .when()
                .log().all()
                .post("/post")
                .then()
                .log().all()
                .statusCode(200)
                .contentType("application/json")
                .and().header("Content-Type", matchesPattern(".*charset=utf-8.*"));
    }
}


