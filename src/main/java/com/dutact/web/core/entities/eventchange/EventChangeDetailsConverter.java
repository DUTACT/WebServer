package com.dutact.web.core.entities.eventchange;

import com.dutact.web.common.mapper.ObjectMapperUtils;
import com.dutact.web.core.entities.eventchange.details.EventChangeDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

@Converter
public class EventChangeDetailsConverter
        implements AttributeConverter<EventChangeDetails, String> {
    private final ObjectMapper objectMapper = ObjectMapperUtils.createObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(EventChangeDetails details) {
        if (details == null) {
            return null;
        }

        return objectMapper.writeValueAsString(details);
    }

    @SneakyThrows
    @Override
    public EventChangeDetails convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return objectMapper.readValue(dbData, EventChangeDetails.class);
    }
}
