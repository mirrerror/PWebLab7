package md.mirrerror.pweblab7.exceptions;

public class InvalidJWTTokenException extends RuntimeException {
    public InvalidJWTTokenException(String message) {
        super(message);
    }
}
