package com.example.weather.service;

import com.example.weather.exception.CityNotFoundException;
import com.example.weather.exception.InternalServerError;
import com.example.weather.model.WeatherForecast;
import com.example.weather.model.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class WeatherService {

    private static final String FORECAST_URL =
            "https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={key}";

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final RestTemplate restTemplate;

    private final String apiKey;

    public WeatherService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.apiKey = "d2929e9483efc82c82c32ee7e02d563e";
    }


    @Cacheable("forecast")
    public WeatherForecast getWeatherForecast(String city) {
        logger.info("Requesting weather forecast for {}", city);
        URI url = new UriTemplate(FORECAST_URL).expand(city, this.apiKey);
        return invoke(url, WeatherForecast.class);
    }

    private <T> T invoke(URI url, Class<T> responseType) {
        RequestEntity<?> request = RequestEntity.get(url)
                .accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<T> exchange = this.restTemplate
                .exchange(request, responseType);
        return exchange.getBody();
    }

    public WeatherResponse getWeatherResponse(String city){
        try{
            WeatherForecast weatherForecast = this.getWeatherForecast(city);
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
        } catch(Exception e){
            if(e.getMessage().contains("city not found")){
                System.out.println("exception is = "+ e.getMessage());
                throw new CityNotFoundException(404, e.getMessage());
            } else {
                throw new InternalServerError(500, e.getMessage());
            }
        }
    }

}
