package com.example.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class WeatherEntry implements Serializable {

    private Instant timestamp;

    private double temperature;

    private double minTemperature;

    private double maxTemperature;
    private Integer weatherId;

    private String weatherIcon;

    private String weatherMain;

    private String weatherDescription;

    private Double windSpeed;

    @JsonProperty("timestamp")
    public Instant getTimestamp() {
        return this.timestamp;
    }

    @JsonSetter("dt")
    public void setTimestamp(long unixTime) {
        this.timestamp = Instant.ofEpochMilli(unixTime * 1000);
    }

    /**
     * Return the temperature in Kelvin (K).
     */
    public double getTemperature() {
        return this.temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getMinTemperature() {
        return this.minTemperature;
    }

    public void setMinTemperature(double temperature) {
        this.minTemperature = temperature;
    }

    public double getMaxTemperature() {
        return this.maxTemperature;
    }

    public void setMaxTemperature(double temperature) {
        this.maxTemperature = temperature;
    }

    @JsonProperty("main")
    public void setMain(Map<String, Object> main) {

        setTemperature(Double.parseDouble(main.get("temp").toString()));
        setMinTemperature(Double.parseDouble(main.get("temp_min").toString()));
        setMaxTemperature(Double.parseDouble(main.get("temp_max").toString()));

    }

    public Integer getWeatherId() {
        return this.weatherId;
    }

    public void setWeatherId(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public String getWeatherIcon() {
        return this.weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public String getWeatherMain() {
        return this.weatherMain;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public String getWeatherDescription() {
        return this.weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public Double getWindSpeed() {
        return this.windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    @JsonProperty("weather")
    public void setWeather(List<Map<String, Object>> weatherEntries) {
        Map<String, Object> weather = weatherEntries.get(0);
        setWeatherId((Integer) weather.get("id"));
        setWeatherIcon((String) weather.get("icon"));
        setWeatherMain((String) weather.get("main"));
        setWeatherDescription((String) weather.get("description"));
    }

    @JsonProperty("wind")
    public void setWind(Map<String, Object> wind) {
        setWindSpeed(Double.parseDouble(wind.get("speed").toString()));
    }

}
