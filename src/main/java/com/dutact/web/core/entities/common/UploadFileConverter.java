package com.dutact.web.core.entities.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.persistence.AttributeConverter;
import lombok.SneakyThrows;

public class UploadFileConverter implements AttributeConverter<UploadedFile, String> {
    @Override
    @SneakyThrows
    public String convertToDatabaseColumn(@Nullable UploadedFile uploadedFile) {
        if (uploadedFile == null) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(uploadedFile);
    }

    @Override
    @SneakyThrows
    public UploadedFile convertToEntityAttribute(@Nullable String dbData) {
        if (dbData == null) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(dbData, UploadedFile.class);
    }
}
