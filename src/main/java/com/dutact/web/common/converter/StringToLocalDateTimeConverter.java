package com.dutact.web.common.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {

        if (source.isEmpty()) {
            return null; // Handle null or empty strings as needed
        }

        // Parse the string as OffsetDateTime
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(source,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
        // Convert to LocalDateTime
        return offsetDateTime.toLocalDateTime();
    }
}
