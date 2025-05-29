package com.example.movieApp.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorMessage {
    private final String exception;
    private final String message;

    public ErrorMessage(Exception exception, String message) {
        this.exception = exception.getClass().getSimpleName();
        this.message = message;
    }
}

