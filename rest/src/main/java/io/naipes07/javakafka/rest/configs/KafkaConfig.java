package io.naipes07.javakafka.rest.configs;

import io.naipes07.javakafka.commons.WeatherMeasurements;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<Long, WeatherMeasurements> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs(), new LongSerializer(), new JsonSerializer<>());
    }

    private Map<String, Object> producerConfigs() {
        return Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9082");
    }

    @Bean
    public KafkaTemplate<Long, WeatherMeasurements> kafkaTemplate(
            ProducerFactory<Long, WeatherMeasurements> kafkaProducerFactory) {
        return new KafkaTemplate<>(kafkaProducerFactory);
    }

}
