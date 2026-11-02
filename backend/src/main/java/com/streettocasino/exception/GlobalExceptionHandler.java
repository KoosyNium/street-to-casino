package com.streettocasino.exception;

import com.streettocasino.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Centralizes exception handling for REST API controllers.
 * Converts application and request errors into consistent
 * JSON responses with appropriate HTTP status codes.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles requests referencing a game session that does not exist.
     *
     * @param e the game session not found exception
     * @return HTTP 404 Not Found with a structured error response
     */
    @ExceptionHandler(GameSessionNotFoundException.class)
    public ResponseEntity<ApiError> handleGameSessionNotFound(
            GameSessionNotFoundException e) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase(),
                                e.getMessage()
                        )
                );
    }

    /**
     * Handles invalid arguments passed to game operations.
     *
     * @param e the invalid argument exception
     * @return HTTP 400 Bad Request with a structured error response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(
            IllegalArgumentException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                e.getMessage()
                        )
                );
    }

    /**
     * Handles unexpected exceptions not covered by specific handlers.
     * Returns a generic error message to avoid exposing internal details.
     *
     * @param e the unexpected exception
     * @return HTTP 500 Internal Server Error with a generic error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(
            Exception e) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiError(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                                "An unexpected error occurred"
                        )
                );
    }

    /**
     * Handles invalid request parameter types, such as malformed UUIDs.
     *
     * @param e the request parameter type mismatch exception
     * @return HTTP 400 Bad Request with a structured error response
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(
            MethodArgumentTypeMismatchException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "Invalid request parameter"
                ));
    }
}
