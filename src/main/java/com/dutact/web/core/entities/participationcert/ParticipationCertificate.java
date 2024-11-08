package com.dutact.web.core.entities.participationcert;

import com.dutact.web.core.entities.Student;
import com.dutact.web.core.entities.event.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

@Getter
@Setter
@Entity
@Table(name = "participation_certificate")
public class ParticipationCertificate {
    @EmbeddedId
    private ParticipationCertificateId id;

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "status", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = ParticipationCertificateStatusConverter.class)
    private ParticipationCertificateStatus status;
}
