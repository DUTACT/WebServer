package com.dutact.web.core.entities.feedback;

import com.dutact.web.core.entities.Student;
import com.dutact.web.core.entities.common.UploadFileConverter;
import com.dutact.web.core.entities.common.UploadedFile;
import com.dutact.web.core.entities.event.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
@Getter
@Setter
public class Feedback {
    @EmbeddedId
    @Column(name = "id")
    private FeedbackId id;

    @Column(name = "content")
    private String content;

    @Column(name = "posted_at")
    private LocalDateTime postedAt;

    @Column(name = "cover_photo", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = UploadFileConverter.class)
    private UploadedFile coverPhoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;
}
