package pl.wm.test;


import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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
    public void lastNameShouldBe_Wong(){
        given()
                .baseUri("https://reqres.in/api")
                .pathParam("userId", 3)
                .when()
                .get("/users/{userId}")
                .then()
                .body("data.last_name", equalTo("Wong"));
    }
}
