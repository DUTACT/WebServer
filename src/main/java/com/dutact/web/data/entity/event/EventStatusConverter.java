package com.dutact.web.data.entity.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.SneakyThrows;

public class EventStatusConverter implements AttributeConverter<EventStatus, String> {
    @Override
    @SneakyThrows
    public String convertToDatabaseColumn(EventStatus eventStatus) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(eventStatus);
    }

    @Override
    @SneakyThrows
    public EventStatus convertToEntityAttribute(String dbData) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(dbData, EventStatus.class);
    }
}
