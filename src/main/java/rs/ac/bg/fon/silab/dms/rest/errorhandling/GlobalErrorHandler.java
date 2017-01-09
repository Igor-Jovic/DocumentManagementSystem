package rs.ac.bg.fon.silab.dms.rest.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rs.ac.bg.fon.silab.dms.core.exception.BadRequestException;

/**
 * @author Slavko Komarica
 */
@ControllerAdvice
public class GlobalErrorHandler {
    // 400
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidMessageFormat(BadRequestException e) {
        return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }
}
