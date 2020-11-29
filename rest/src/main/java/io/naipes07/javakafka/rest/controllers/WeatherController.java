package io.naipes07.javakafka.rest.controllers;

import io.naipes07.javakafka.commons.WeatherMeasurements;
import io.naipes07.javakafka.rest.services.WeatherService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    private WeatherService weatherService;

    @PostMapping
    public void collect(WeatherMeasurements weatherMeasurements) {
        weatherService.send(weatherMeasurements);
    }

}
