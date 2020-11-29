package io.naipes07.javakafka.streams.configs;

import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AlertsProcessorConfig {

    @Bean
    public Consumer<KStream<Long, WeatherMeasurementsAverage>> alertsProcessor() {
        return input -> input.foreach((key, value) -> {
            if (value.getHumidityAverage() > 51d) {
                log.warn("Threshold reached: [{}]", value);
            }
        });
    }

}
