package com.example.movieApp.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Violation {

    private String fieldName;

    private String message;

    public Violation(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

}
