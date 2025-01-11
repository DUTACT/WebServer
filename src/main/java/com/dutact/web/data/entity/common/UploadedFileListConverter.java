package com.dutact.web.data.entity.common;

import com.dutact.web.common.mapper.ObjectMapperUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

@Converter
public class UploadedFileListConverter implements AttributeConverter<List<UploadedFile>, String> {
    private final ObjectMapper objectMapper = ObjectMapperUtils.createObjectMapper();

    @Override
    @SneakyThrows
    public String convertToDatabaseColumn(List<UploadedFile> attribute) {
        if (attribute == null) {
            return objectMapper.writeValueAsString(new ArrayList<>());
        }
        return objectMapper.writeValueAsString(attribute);
    }

    @Override
    @SneakyThrows
    public List<UploadedFile> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(dbData, new TypeReference<List<UploadedFile>>() {});
    }
} 