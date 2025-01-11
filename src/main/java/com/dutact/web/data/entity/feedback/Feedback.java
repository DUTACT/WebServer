package com.dutact.web.data.entity.feedback;

import com.dutact.web.data.entity.Student;
import com.dutact.web.data.entity.common.UploadedFile;
import com.dutact.web.data.entity.common.UploadedFileListConverter;
import com.dutact.web.data.entity.event.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "feedback")
@Getter
@Setter
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "posted_at")
    private LocalDateTime postedAt;

    @Column(name = "cover_photos", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = UploadedFileListConverter.class)
    private List<UploadedFile> coverPhotos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @PrePersist
    public void prePersist() {
        postedAt = LocalDateTime.now();
    }
}
