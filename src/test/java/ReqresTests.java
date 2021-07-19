import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;


public class ReqresTests {
    @Test
    void listUserTest() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);
    }
    @Test
    void singleUserNotFoundTest() {
        given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404);
    }
    @Test
    void createTest() {
        given()
                .contentType(JSON)
                .body("{\"name\": \"morpheus\"," +
                        "\"job\": \"leader\"}")
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }
    @Test
    void registerSuccessfulTest() {
        given()
                .contentType(JSON)
        .body("{\"email\": \"eve.holt@reqres.in\"," +
                "\"password\": \"pistol\"}")
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
    @Test
    void loginSuccessfulTest() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"eve.holt@reqres.in\"," +
                        "\"password\": \"cityslicka\"}")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
    @Test
    void unsuccessfulRegistrationTest() {
        given()
                .contentType(JSON)
                .body("{\"email\": \"sydney@fife\"}")
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
    @Test
    void deleteUser() {
        String response =
                given()
                        .contentType(JSON)
                        .body("{\"name\": \"morpheus\"," + "\"job\": \"zion resident\"}")
                        .when()
                        .post("https://reqres.in/api/users/2")
                        .then()
                        .statusCode(201)
                        .extract().path("id");

        delete("https://reqres.in/api/users/" + response)
                .then()
                .statusCode(204);
    }
}
