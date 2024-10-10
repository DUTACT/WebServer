package com.dutact.web.core.entities.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.SneakyThrows;

public class UploadFileConverter implements AttributeConverter<UploadedFile, String> {
    @Override
    @SneakyThrows
    public String convertToDatabaseColumn(UploadedFile uploadedFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(uploadedFile);
    }

    @Override
    @SneakyThrows
    public UploadedFile convertToEntityAttribute(String dbData) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(dbData, UploadedFile.class);
    }
}
