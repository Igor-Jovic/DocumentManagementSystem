package rs.ac.bg.fon.silab.dms.rest;


import com.jayway.restassured.RestAssured;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.bg.fon.silab.dms.DocumentManagementSystemApp;

import java.io.File;
import java.io.IOException;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = DocumentManagementSystemApp.class)
public abstract class RestIntegrationTest {

    private static final String MOCK_DATA_ROOT = "mockdata";

    @LocalServerPort
    int port;

    @Before
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

    }

    public String readJson(String path) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(MOCK_DATA_ROOT + path).getFile());
        return FileUtils.readFileToString(file);
    }
}