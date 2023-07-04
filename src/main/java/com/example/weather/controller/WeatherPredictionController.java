package com.example.weather.controller;

import com.example.weather.model.ErrorResponse;
import com.example.weather.model.WeatherForecast;
import com.example.weather.model.WeatherResponse;
import com.example.weather.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Tag(description = "Weather Prediction API", name = "Weather App")
@RestController
@RequestMapping("/api/weather")
public class WeatherPredictionController {

    private final WeatherService weatherService;

    public WeatherPredictionController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Operation(summary = "Weather Prediction Endpoint",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns the weather prediction",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content)
            })
    @GetMapping("/{city}")
    public WeatherResponse getWeatherForecast(@PathVariable String city) {
        WeatherResponse weatherResponse = weatherService.getWeatherResponse(city);
        return weatherResponse;
    }

}
