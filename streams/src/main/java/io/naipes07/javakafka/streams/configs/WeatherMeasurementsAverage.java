package io.naipes07.javakafka.streams.configs;

import io.naipes07.javakafka.commons.WeatherTimestamped;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherMeasurementsAverage implements WeatherTimestamped {

    private Long deviceId;
    private Double temperatureAverage;
    private Double humidityAverage;
    private Instant timestamp;

    @Override
    public long getTimestamp() {
        return timestamp == null ? System.currentTimeMillis() : timestamp.toEpochMilli();
    }
}
