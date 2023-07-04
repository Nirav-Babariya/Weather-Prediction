package com.example.weather.exception;

import com.example.weather.model.ErrorResponse;

public class InternalServerError extends RuntimeException{

    private ErrorResponse errorResponse;

    public InternalServerError (int code, String message){
        super(message);
        errorResponse.setCode(code);
        errorResponse.setErrorMessage(message);
    }
}
