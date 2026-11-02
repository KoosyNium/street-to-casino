package com.streettocasino.dto;

public record ApiError(
        int status,
        String error,
        String message
) {}
