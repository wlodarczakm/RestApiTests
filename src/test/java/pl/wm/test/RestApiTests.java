package pl.wm.test;


import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestApiTests {

    @Test
    public void serverShouldReturn200() {
        given()
                .when()
                .get("https://reqres.in/api/users/3")
                .then()
                .statusCode(200);
    }
}
