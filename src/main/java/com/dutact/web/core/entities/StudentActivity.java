package com.dutact.web.core.entities;

import com.dutact.web.core.entities.event.Event;
import com.dutact.web.features.activity.dto.ActivityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_activity")
@Getter
@Setter
public class StudentActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "feedback_id")
    private Integer feedbackId;
} 