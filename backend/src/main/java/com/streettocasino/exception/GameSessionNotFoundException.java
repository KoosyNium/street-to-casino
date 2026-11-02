package com.streettocasino.exception;

/**
 * Exception thrown when a requested game session does not exist.
 */
public class GameSessionNotFoundException extends RuntimeException {

    /**
     * Creates an exception with the specified error message.
     *
     * @param message the description of the missing game session
     */
    public GameSessionNotFoundException(String message) {
        super(message);
    }
}
