package com.dutact.web.data.entity.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import lombok.SneakyThrows;

public class PostStatusConverter implements AttributeConverter<PostStatus, String> {
    @Override
    @SneakyThrows
    public String convertToDatabaseColumn(PostStatus postStatus) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(postStatus);
    }

    @Override
    @SneakyThrows
    public PostStatus convertToEntityAttribute(String dbData) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(dbData, PostStatus.class);
    }
}
