package io.naipes07.javakafka.commons;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.Instant;
import lombok.Data;

@Data
public class WeatherMeasurements implements WeatherTimestamped {

    private Long deviceId;
    private Float temperatureCelsius;
    private Float humidity;
    @JsonFormat(shape = Shape.STRING)
    private Instant timestamp;

    @Override
    public long getTimestamp() {
        return timestamp.toEpochMilli();
    }

}
