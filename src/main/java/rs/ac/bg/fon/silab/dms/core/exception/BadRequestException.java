package rs.ac.bg.fon.silab.dms.core.exception;

import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Service
public class BadRequestException extends Exception implements ExceptionMapper<BadRequestException> {

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public Response toResponse(BadRequestException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
