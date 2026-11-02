package com.streettocasino.dto;

/**
 * Represents a standardized error response returned by the REST API.
 *
 * @param status the HTTP status code
 * @param error the HTTP error description
 * @param message a human-readable description of the error
 */
public record ApiError(
        int status,
        String error,
        String message
) {}
