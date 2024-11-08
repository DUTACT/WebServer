package com.dutact.web.core.entities.participationcert;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ParticipationCertificateId implements Serializable {
    private Integer eventId;
    private Integer studentId;
}
