package com.dutact.web.core.entities.eventchange;

import com.dutact.web.core.entities.eventchange.details.EventChangeDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import lombok.SneakyThrows;

public class EventChangeDetailsConverter
        implements AttributeConverter<EventChangeDetails, String> {
    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(EventChangeDetails details) {
        if (details == null) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(details);
    }

    @SneakyThrows
    @Override
    public EventChangeDetails convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.readValue(dbData, EventChangeDetails.class);
    }
}
