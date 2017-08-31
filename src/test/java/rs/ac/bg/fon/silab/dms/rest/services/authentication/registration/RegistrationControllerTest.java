package rs.ac.bg.fon.silab.dms.rest.services.authentication.registration;

import org.apache.http.HttpStatus;
import org.junit.Test;
import rs.ac.bg.fon.silab.dms.rest.RestIntegrationTest;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.Ignore;

@Ignore
public class RegistrationControllerTest extends RestIntegrationTest {

    private static final String MOCK_ROOT_PATH = "/authentication";

    @Test
    public void createUser_returnsNewUser() throws IOException {
        String registrationRequest = readJson(MOCK_ROOT_PATH + "/newUserWithCompany.json");

        givenJson().body(registrationRequest)
                .when().post("/auth/registration")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("content.message", equalTo("Registration successful for admin: newUser@newCompany, you can now login."));
    }

}
