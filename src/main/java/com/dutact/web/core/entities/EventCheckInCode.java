package com.dutact.web.core.entities;

import com.dutact.web.core.entities.event.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "event_checkin_code")
public class EventCheckInCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "title")
    private String title;

    @PrePersist
    public void prePersist() {
        startAt = startAt.withSecond(0).withNano(0);
        endAt = endAt.withSecond(0).withNano(0);
    }
}
