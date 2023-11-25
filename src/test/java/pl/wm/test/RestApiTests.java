package pl.wm.test;


import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.mapper.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
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

    @Test
    public void verifySumOfResponseObjects(){

        RestAssured.baseURI = "https://fakerestapi.azurewebsites.net/api/v1";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/Authors");

        ResponseBody rBody = response.getBody();

        String keyWord = "idBook";

        int counter = 0;

        int position = rBody.asString().indexOf(keyWord);
        while (position != -1) {
            counter++;
            position = rBody.asString().indexOf(keyWord, position + 1);
        }
        System.out.println(counter);

        String jsonResponse = response.getBody().asString();
        JsonPath jsonPath = new JsonPath(jsonResponse);
        int numberOfResponseObjects = jsonPath.getList("collection").size();
        System.out.println(numberOfResponseObjects);

        Assert.assertEquals(counter, numberOfResponseObjects, "OK");
    }

    @Test
    public void validateResponseJsonObjects() {
        RestAssured.baseURI = "https://fakerestapi.azurewebsites.net/api/v1";

        Response response = given()
                .get("/Authors")
                .then()
                .extract()
                .response();

        response
                .then()
                .body("id", everyItem(notNullValue()))
                .body("idBook", everyItem(notNullValue()))
                .body("firstName", everyItem(notNullValue()))
                .body("lastName", everyItem(notNullValue()));
        System.out.println(response.asString());
    }
}


