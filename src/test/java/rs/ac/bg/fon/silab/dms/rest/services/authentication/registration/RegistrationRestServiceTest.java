package rs.ac.bg.fon.silab.dms.rest.services.authentication.registration;

import org.junit.Test;
import rs.ac.bg.fon.silab.dms.rest.RestIntegrationTest;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;

public class RegistrationRestServiceTest extends RestIntegrationTest {

    private static final String ROOT_PATH = "/authentication";

    @Test
    public void createUser_returnsNewUser() throws IOException {

        String registrationRequest = readJson(ROOT_PATH + "/newUserWithCompany.json");

        given().contentType(JSON).body(registrationRequest)
                .when().post("/auth/registration")
                .then()
                .statusCode(200);
    }
}
