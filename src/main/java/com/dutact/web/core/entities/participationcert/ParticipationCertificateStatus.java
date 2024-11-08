package com.dutact.web.core.entities.participationcert;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ParticipationCertificateStatus.Confirmed.class, name = ParticipationCertificateStatus.Confirmed.TYPE_NAME),
        @JsonSubTypes.Type(value = ParticipationCertificateStatus.Rejected.class, name = ParticipationCertificateStatus.Rejected.TYPE_NAME),
})
public abstract class ParticipationCertificateStatus {
    public static class Confirmed extends ParticipationCertificateStatus {
        public static final String TYPE_NAME = "confirmed";
    }

    @Getter
    @Setter
    public static class Rejected extends ParticipationCertificateStatus {
        public static final String TYPE_NAME = "rejected";
        private String reason;
    }
}
