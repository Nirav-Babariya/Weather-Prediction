package com.example.weather.model;

import lombok.Data;

@Data
public class ErrorResponse {

    private int code;
    private String errorMessage;
}
