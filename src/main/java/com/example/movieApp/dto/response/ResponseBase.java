package com.example.movieApp.dto.response;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class ResponseBase<T> {
    private int statusCode;
    @Nullable
    private T data;
    private String message;

    public ResponseBase(int statusCode, T data, String message){
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
    }
}