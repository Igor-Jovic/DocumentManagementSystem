package rs.ac.bg.fon.silab.dms.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.security.exception.UnknownUserException;

@ControllerAdvice
public class GlobalErrorHandler {
    // 400
    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleInvalidMessageFormat(BadRequestException e) {
        return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }

    @ExceptionHandler({UnknownUserException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleUserNotFound(BadRequestException e) {
        return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }
}
