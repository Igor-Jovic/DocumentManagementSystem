package rs.ac.bg.fon.silab.dms.security.exception;

/**
 * Created by igor on 1/10/17.
 */
public class UnknownUserException extends RuntimeException {
    public UnknownUserException(String message) {
        super(message);
    }
}
