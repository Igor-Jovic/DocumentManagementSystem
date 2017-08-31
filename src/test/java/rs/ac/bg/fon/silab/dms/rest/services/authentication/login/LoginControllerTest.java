package rs.ac.bg.fon.silab.dms.rest.services.authentication.login;

import org.apache.http.HttpStatus;
import org.junit.Test;
import rs.ac.bg.fon.silab.dms.rest.RestIntegrationTest;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.login.dto.LoginRequest;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.Ignore;

@Ignore
public class LoginControllerTest extends RestIntegrationTest {

    @Test
    public void getCurrentUser_loggedInAsAdmin_returnsAdminInfo() throws IOException {
        givenUser("admin", "admin")
                .when().get("/auth/user")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("content.username", equalTo("admin"))
                .body("content.userRole", equalTo("ADMIN"));
    }

    @Test
    public void logout_loggedInAsAdmin_forbiden() throws IOException {
        givenUser("admin", "admin")
                .when().delete("/auth/logout")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("errorMessage", equalTo("Logged out"));
    }

    @Test
    public void login_userExists_returnsUserInfo() throws IOException {
        LoginRequest loginRequest = new LoginRequest("admin", "admin");

        givenJson().body(loginRequest)
                .when().post("/auth/login")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("content.username", equalTo("admin"))
                .body("content.userRole", equalTo("ADMIN"));
    }

    @Test
    public void login_userDoesntExist_badRequest() throws IOException {
        LoginRequest loginRequest = new LoginRequest("notExisting", "notExisting");

        givenJson().body(loginRequest)
                .when().post("/auth/login")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("errorMessage", equalTo("Could not find user with username: notExisting"));
    }


}