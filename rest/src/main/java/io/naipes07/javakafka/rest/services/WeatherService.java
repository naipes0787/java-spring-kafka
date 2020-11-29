package io.naipes07.javakafka.rest.services;

import io.naipes07.javakafka.commons.WeatherMeasurements;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WeatherService {

    private final KafkaTemplate<Long, WeatherMeasurements> kafkaTemplate;

    public WeatherService(KafkaTemplate<Long, WeatherMeasurements> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(WeatherMeasurements weatherMeasurements) {
        kafkaTemplate.send("weather-data", weatherMeasurements.getDeviceId(), weatherMeasurements);
        log.info("Sent: {}", weatherMeasurements);
    }

}
