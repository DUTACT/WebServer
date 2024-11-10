package com.dutact.web.core.entities.eventregistration.participationcert;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import jakarta.persistence.AttributeConverter;
import lombok.SneakyThrows;

public class ParticipationCertificateStatusConverter
        implements AttributeConverter<ParticipationCertificateStatus, String> {
    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(@Nullable ParticipationCertificateStatus participationCertificateStatus) {
        if (participationCertificateStatus == null) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(participationCertificateStatus);
    }

    @SneakyThrows
    @Override
    public ParticipationCertificateStatus convertToEntityAttribute(@Nullable String s) {
        if (s == null) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(s, ParticipationCertificateStatus.class);
    }
}
