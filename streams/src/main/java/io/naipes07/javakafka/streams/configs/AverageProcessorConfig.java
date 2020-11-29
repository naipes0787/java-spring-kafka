package io.naipes07.javakafka.streams.configs;

import io.naipes07.javakafka.commons.WeatherMeasurements;
import java.time.Duration;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Suppressed;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class AverageProcessorConfig {

    @Bean
    public Function<KStream<Long, WeatherMeasurements>, KStream<Long, WeatherMeasurementsAverage>> averagerProcessor() {
        return input ->
                input.groupByKey()
                        .windowedBy(TimeWindows.of(Duration.ofSeconds(5)).grace(Duration.ZERO))
                        .aggregate(this::init, this::aggregate,
                                Materialized.with(Serdes.Long(), new JsonSerde<>(IntermediateAggregationState.class)))
                        .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()))
                        .toStream()
                        .map(this::average);
    }

    private KeyValue<Long, WeatherMeasurementsAverage> average(Windowed<Long> longWindowed,
            IntermediateAggregationState intermediateAggregationState) {
        return KeyValue.pair(longWindowed.key(), new WeatherMeasurementsAverage(
                longWindowed.key(),
                intermediateAggregationState.getTemperatureSum() / intermediateAggregationState.getTemperatureCount(),
                intermediateAggregationState.getHumiditySum() / intermediateAggregationState.getHumidityCount(),
                longWindowed.window().endTime()
        ));
    }

    private IntermediateAggregationState aggregate(Long deviceId, WeatherMeasurements weatherMeasurements,
            IntermediateAggregationState intermediateAggregationState) {
        return new IntermediateAggregationState(
                intermediateAggregationState.getTemperatureCount() + 1,
                intermediateAggregationState.getTemperatureSum() + weatherMeasurements.getTemperatureCelsius(),
                intermediateAggregationState.getHumidityCount() + 1,
                intermediateAggregationState.getHumiditySum() + weatherMeasurements.getHumidity()
        );
    }

    private IntermediateAggregationState init() {
        return new IntermediateAggregationState();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class IntermediateAggregationState {

        private long temperatureCount;
        private double temperatureSum;
        private long humidityCount;
        private double humiditySum;

    }

}
