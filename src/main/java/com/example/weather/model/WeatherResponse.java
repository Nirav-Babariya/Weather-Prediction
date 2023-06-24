package com.example.weather.model;

import lombok.Data;

@Data
public class WeatherResponse {
    private double highTemp;
    private double lowTemp;
    private String message;
}
