package md.mirrerror.pweblab7.exceptions;

public class UserWithThisEmailAlreadyExistsException extends RuntimeException {
    public UserWithThisEmailAlreadyExistsException(String message) {
        super(message);
    }
}
