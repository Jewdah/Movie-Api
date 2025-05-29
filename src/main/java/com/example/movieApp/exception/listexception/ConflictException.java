package com.example.movieApp.exception.listexception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConflictException extends RuntimeException{
    private static final String DESCRIPTION = "Conflict exception (409)";

    public ConflictException(String detail) {super(detail); }
}
