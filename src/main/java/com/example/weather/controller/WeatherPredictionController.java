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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Tag(description = "Weather Prediction API", name = "Weather App")
@Controller
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
    @PostMapping("/welcome")
    public ModelAndView getWeatherForecast(ModelMap model, @RequestParam String city) {
        model.put("city", city);
        WeatherResponse weatherResponse = weatherService.getWeatherResponse(city);
        model.put("weather", weatherResponse);
        return new ModelAndView("weatherPage", model);
    }

    @GetMapping("/welcome")
    public ModelAndView getWeatherForecastWelcome(ModelMap model) {
        return new ModelAndView("welcome");
    }

}
