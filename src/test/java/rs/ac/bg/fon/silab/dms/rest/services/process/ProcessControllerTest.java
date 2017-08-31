package rs.ac.bg.fon.silab.dms.rest.services.process;

import org.apache.http.HttpStatus;
import org.junit.Test;
import rs.ac.bg.fon.silab.dms.rest.RestIntegrationTest;
import rs.ac.bg.fon.silab.dms.rest.services.process.dto.ProcessRequest;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import org.junit.Ignore;

@Ignore
public class ProcessControllerTest extends RestIntegrationTest {

    @Test
    public void createProcess_loggedInAsAdmin_createsProcess() throws IOException {
        ProcessRequest processRequest = new ProcessRequest();
        String processName = "New Process";
        processRequest.setName(processName);
        processRequest.setParentProcess(null);
        processRequest.setPrimitive(false);


        givenUser("admin", "admin").body(processRequest)
                .when().post("/processes")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("content.name", equalTo(processName))
                .body("content.isPrimitive", equalTo(false))
                .body("content.id", greaterThan(-1));
    }

    @Test
    public void createProcess_loggedInAsNotAdmin_forbidden() throws IOException {
        ProcessRequest processRequest = new ProcessRequest();
        String processName = "New Process";
        processRequest.setName(processName);
        processRequest.setParentProcess(null);
        processRequest.setPrimitive(false);


        givenUser("notAdmin", "notAdmin").body(processRequest)
                .when().post("/processes")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

}