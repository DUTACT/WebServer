package com.dutact.web.features.notification.core.timer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "scheduled_job", schema = "notification")
public class ScheduledJob {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "compare_string")
    private String compareString;

    @Column(name = "details")
    private String details;

    @Column(name = "type")
    private String type;

    @Column(name = "fire_at")
    private LocalDateTime fireAt;

    @Column(name = "expire_at")
    private LocalDateTime expireAt;
}
