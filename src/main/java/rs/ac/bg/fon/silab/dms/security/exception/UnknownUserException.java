package rs.ac.bg.fon.silab.dms.security.exception;

public class UnknownUserException extends RuntimeException {
    public UnknownUserException(String message) {
        super(message);
    }
}
