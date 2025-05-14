package md.mirrerror.pweblab7.exceptions.handlers;

import md.mirrerror.pweblab7.exceptions.*;
import md.mirrerror.pweblab7.responses.InformationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            InvalidJWTTokenException.class,
            InvalidCredentialsException.class
    })
    public ResponseEntity<InformationResponse> handleBadRequest(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new InformationResponse(exception.getMessage()));
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            TVSeriesNotFoundException.class
    })
    public ResponseEntity<InformationResponse> handleNotFound(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new InformationResponse(exception.getMessage()));
    }

    @ExceptionHandler({
            UserNotAuthenticatedException.class
    })
    public ResponseEntity<InformationResponse> handleUnauthorized(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new InformationResponse(exception.getMessage()));
    }

    @ExceptionHandler({
            UserWithThisEmailAlreadyExistsException.class
    })
    public ResponseEntity<InformationResponse> handleConflict(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new InformationResponse(exception.getMessage()));
    }

    @ExceptionHandler({
            InsufficientPermissionsException.class
    })
    public ResponseEntity<InformationResponse> handleForbidden(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new InformationResponse(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<InformationResponse> handleAllOtherExceptions(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new InformationResponse("An unexpected error occurred: " + exception.getMessage()));
    }
}
