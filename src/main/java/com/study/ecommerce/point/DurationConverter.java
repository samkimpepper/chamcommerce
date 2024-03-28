package com.study.ecommerce.point;

import java.time.Duration;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class DurationConverter implements AttributeConverter<Duration, String> {
    @Override
    public String convertToDatabaseColumn(Duration duration) {
        return duration != null ? duration.toString() : null;
    }

    @Override
    public Duration convertToEntityAttribute(String dbData) {
        return dbData != null ? Duration.parse(dbData) : null;
    }
}
