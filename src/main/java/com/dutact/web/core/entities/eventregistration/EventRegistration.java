package com.dutact.web.core.entities.eventregistration;

import com.dutact.web.core.entities.Student;
import com.dutact.web.core.entities.event.CannotChangeStatusException;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.event.EventStatus;
import com.dutact.web.core.entities.eventregistration.participationcert.ParticipationCertificateStatus;
import com.dutact.web.core.entities.eventregistration.participationcert.ParticipationCertificateStatusConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_registration")
@Getter
@Setter
public class EventRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

//    @Column(name = "certificate_status", columnDefinition = "jsonb")
//    @ColumnTransformer(write = "?::jsonb")
//    @Convert(converter = ParticipationCertificateStatusConverter.class)
//    private ParticipationCertificateStatus certificateStatus = new ParticipationCertificateStatus.Pending();
//
//    public void setCertificateStatus(ParticipationCertificateStatus certificateStatus) throws CannotChangeStatusException {
//        this.certificateStatus = this.certificateStatus.changeStatus(certificateStatus);
//    }
}
