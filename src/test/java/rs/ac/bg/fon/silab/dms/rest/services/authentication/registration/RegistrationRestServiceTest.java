package rs.ac.bg.fon.silab.dms.rest.services.authentication.registration;

import org.apache.http.HttpStatus;
import org.junit.Test;
import rs.ac.bg.fon.silab.dms.rest.RestIntegrationTest;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.core.IsEqual.equalTo;

public class RegistrationRestServiceTest extends RestIntegrationTest {

    private static final String ROOT_PATH = "/authentication";

    @Test
    public void createUser_returnsNewUser() throws IOException {
        String registrationRequest = readJson(ROOT_PATH + "/newUserWithCompany.json");

        givenJson().body(registrationRequest)
                .when().post("/auth/registration")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("content.message", equalTo("Registration successful for admin: newUser@newCompany, you can now login."));
    }

    @Test
    public void loginAsAdmin_returnsAdminInfo() throws IOException {
        givenUser("admin", "admin")
                .when().get("/auth/user")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("content.username", equalTo("admin"))
                .body("content.userRole", equalTo("ADMIN"));
    }
}
