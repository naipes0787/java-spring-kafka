package io.naipes07.javakafka.streams;

import io.naipes07.javakafka.commons.WeatherTimestamped;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

@Slf4j
public class WeatherTimestampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        Object value = consumerRecord.value();
        if (value instanceof WeatherTimestamped) {
            return ((WeatherTimestamped) value).getTimestamp();
        } else {
            log.warn("Unknown class");
            return System.currentTimeMillis();
        }
    }

}
