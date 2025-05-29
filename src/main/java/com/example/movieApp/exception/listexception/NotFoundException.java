package com.example.movieApp.exception.listexception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NotFoundException extends RuntimeException {
    private static final String DESCRIPTION = "Not found exception (404)";

    public NotFoundException(String detail) {
        super(detail);

    }
}
