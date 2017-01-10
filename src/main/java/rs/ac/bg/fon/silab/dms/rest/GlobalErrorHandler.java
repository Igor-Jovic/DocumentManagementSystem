package rs.ac.bg.fon.silab.dms.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;
import rs.ac.bg.fon.silab.dms.security.exception.UnknownUserException;

@ControllerAdvice
public class GlobalErrorHandler {
    // 400
    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleInvalidRequestFormat(Throwable e) {
        return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }

    @ExceptionHandler({UnknownUserException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleUserNotFound(Throwable e) {
        return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }
}
