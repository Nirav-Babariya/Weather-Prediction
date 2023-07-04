package com.example.weather.exception;

import com.example.weather.model.ErrorResponse;

public class CityNotFoundException extends RuntimeException{

    private final ErrorResponse errorResponse = new ErrorResponse();

    public CityNotFoundException(int code, String message){
        super(message);
        errorResponse.setCode(code);
        errorResponse.setErrorMessage(message);
    }

}
