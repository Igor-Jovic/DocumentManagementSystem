package rs.ac.bg.fon.silab.dms.rest;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Component
@Path("/api-status")
public class HomeController {

    @GET
    public Response hello() {
        return Response.ok("Server is up and running.")
                .build();
    }
}
