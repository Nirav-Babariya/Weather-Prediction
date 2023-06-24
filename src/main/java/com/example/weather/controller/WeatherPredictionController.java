package com.example.weather.controller;

import com.example.weather.model.WeatherForecast;
import com.example.weather.model.WeatherResponse;
import com.example.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/api/weather")
public class WeatherPredictionController {

    private final WeatherService weatherService;

    public WeatherPredictionController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Operation(summary = "Weather Prediction")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = WeatherForecast.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @RequestMapping("/{city}")
    public WeatherResponse getWeatherForecast(@PathVariable String city) {
        WeatherForecast weatherForecast = weatherService.getWeatherForecast(city);
        WeatherResponse weatherResponse = new WeatherResponse();
        double count = 0.0d;
        double temp = 0.0d;
        boolean willRain = false;
        boolean thunderStorms = false;
        double highTemp = 0.0d;
        double lowTemp = 500.0d;

        for(int i=0;i< weatherForecast.getEntries().size();i++){
            if(weatherForecast.getEntries().get(i).getTimestamp().isBefore(Instant.now().plus(3, ChronoUnit.DAYS))){
                highTemp = weatherForecast.getEntries().get(i).getMaxTemperature() > highTemp ? weatherForecast.getEntries().get(i).getMaxTemperature() : highTemp;
                lowTemp = weatherForecast.getEntries().get(i).getMinTemperature() < lowTemp ? weatherForecast.getEntries().get(i).getMinTemperature() : lowTemp;
                temp += weatherForecast.getEntries().get(i).getTemperature();
                count++;
                if(weatherForecast.getEntries().get(i).getWeatherMain().equals("Rain")){
                    willRain = true;
                }
                if(weatherForecast.getEntries().get(i).getWeatherMain().contains("storm")){
                    thunderStorms = true;
                }
            }
        }
        double avgTemp = temp/count;
        System.out.println("Avg is = " + temp/count);
        String message = "";
        if(willRain){
            message = "Carry umbrella";
        } else if(avgTemp >= 313.15){
            message = "Use sunscreen lotion";
        } else if(thunderStorms){
            message = "Don’t step out! A Storm is brewing!";
        } else {
            message = "Clear Sky!";
        }
        weatherResponse.setHighTemp(highTemp - 273.15);
        weatherResponse.setLowTemp(lowTemp - 273.15);
        weatherResponse.setMessage(message);
        return weatherResponse;
    }

}
