package com.dutact.web.common.notification.core;

import com.dutact.web.common.mapper.ObjectMapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

@Converter
public class NotificationDetailsConverter implements AttributeConverter<Object, String> {
    private final ObjectMapper objectMapper = ObjectMapperUtils.createObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(Object attribute) {
        return objectMapper.writeValueAsString(attribute);
    }

    @SneakyThrows
    @Override
    public Object convertToEntityAttribute(String dbData) {
        return objectMapper.readValue(dbData, Object.class);
    }
}
