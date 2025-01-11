package com.dutact.web.data.entity.checkincode;

import com.dutact.web.common.mapper.ObjectMapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

@Converter
public class CheckInLocationConverter implements AttributeConverter<CheckInLocation, String> {
    private final ObjectMapper objectMapper = ObjectMapperUtils.createObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(@Nullable CheckInLocation attribute) {
        if (attribute == null) {
            return null;
        }

        return objectMapper.writeValueAsString(attribute);
    }

    @SneakyThrows
    @Override
    public CheckInLocation convertToEntityAttribute(@Nullable String dbData) {
        if (dbData == null) {
            return null;
        }

        return objectMapper.readValue(dbData, CheckInLocation.class);
    }
}
