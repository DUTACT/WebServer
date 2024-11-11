package com.dutact.web.core.entities.eventregistration.participationcert;

import com.dutact.web.core.entities.event.CannotChangeStatusException;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ParticipationCertificateStatus.Confirmed.class, name = ParticipationCertificateStatus.Confirmed.TYPE_NAME),
        @JsonSubTypes.Type(value = ParticipationCertificateStatus.Rejected.class, name = ParticipationCertificateStatus.Rejected.TYPE_NAME),
        @JsonSubTypes.Type(value = ParticipationCertificateStatus.Pending.class, name = ParticipationCertificateStatus.Pending.TYPE_NAME)
})
public abstract class ParticipationCertificateStatus {
    /**
     * Change the status of the participation certificate
     *
     * @return the new status
     */
    public abstract ParticipationCertificateStatus changeStatus(ParticipationCertificateStatus newStatus)
            throws CannotChangeStatusException;

    @Getter
    @Setter
    public static class Confirmed extends ParticipationCertificateStatus {
        public static final String TYPE_NAME = "confirmed";
        private String moderatedAt;

        public Confirmed() {
            this.moderatedAt = LocalDateTime.now().toString();
        }

        @Override
        public ParticipationCertificateStatus changeStatus(ParticipationCertificateStatus newStatus)
                throws CannotChangeStatusException {
            throw new CannotChangeStatusException("Cannot change status from confirmed to " +
                    newStatus.getClass().getSimpleName());
        }
    }

    @NoArgsConstructor
    public static class Pending extends ParticipationCertificateStatus {
        public static final String TYPE_NAME = "pending";

        @Override
        public ParticipationCertificateStatus changeStatus(ParticipationCertificateStatus newStatus)
                throws CannotChangeStatusException {
            return newStatus;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Rejected extends ParticipationCertificateStatus {
        public static final String TYPE_NAME = "rejected";
        private String reason;
        private String moderatedAt;

        public Rejected(String reason) {
            this.reason = reason;
            this.moderatedAt = LocalDateTime.now().toString();
        }

        @Override
        public ParticipationCertificateStatus changeStatus(ParticipationCertificateStatus newStatus)
                throws CannotChangeStatusException {
            throw new CannotChangeStatusException("Cannot change status from rejected to " +
                    newStatus.getClass().getSimpleName());
        }
    }
}