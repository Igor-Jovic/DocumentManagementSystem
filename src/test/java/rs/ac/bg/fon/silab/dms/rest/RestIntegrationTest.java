package rs.ac.bg.fon.silab.dms.rest;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.bg.fon.silab.dms.DocumentManagementSystemApp;
import rs.ac.bg.fon.silab.dms.rest.services.authentication.login.dto.LoginResponse;
import rs.ac.bg.fon.silab.dms.testUtil.AuthenticationApi;

import java.io.File;
import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = DocumentManagementSystemApp.class)
public abstract class RestIntegrationTest {

    private static final String MOCK_DATA_ROOT = "mockdata";

    @LocalServerPort
    int port;

    @Value("#{'${auth.headerName}'}")
    protected String authenticationHeader;

    private AuthenticationApi authApi;

    @Before
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        authApi = new AuthenticationApi("http://localhost:" + port + "/auth/login");
    }

    protected RequestSpecification givenUser(String username, String password) throws IOException {
        LoginResponse loginResponse = authApi.loginAs(username, password);
        return givenJson().and()
                .given().header(new Header(authenticationHeader, loginResponse.token));
    }

    protected RequestSpecification givenJson() {
        return given().contentType(ContentType.JSON);
    }

    protected String readJson(String path) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(MOCK_DATA_ROOT + path).getFile());
        return FileUtils.readFileToString(file);
    }
}