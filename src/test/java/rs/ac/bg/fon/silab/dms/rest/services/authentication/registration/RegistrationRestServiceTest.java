package rs.ac.bg.fon.silab.dms.rest.services.authentication.registration;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rs.ac.bg.fon.silab.dms.DocumentManagementSystemApp;
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

        System.out.println(registrationRequest);
        given().contentType(JSON).body(registrationRequest).when().post("/auth/registration").then()
                .statusCode(200)
                .body("data.content.username", equalTo("newUser"))
                .body("data.content.role", equalTo("ADMIN"));
    }
}