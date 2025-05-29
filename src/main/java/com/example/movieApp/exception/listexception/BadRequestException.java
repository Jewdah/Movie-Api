package com.example.movieApp.exception.listexception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BadRequestException extends RuntimeException {
    private static final String DESCRIPTION = "Bad request exception (400)";

    public BadRequestException(String detail) {
        super(detail);
    }
}
