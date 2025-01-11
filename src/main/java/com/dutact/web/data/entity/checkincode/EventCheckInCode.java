package com.dutact.web.data.entity.checkincode;

import com.dutact.web.data.entity.event.Event;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

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

    @Nullable
    @Column(name = "location", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = CheckInLocationConverter.class)
    private CheckInLocation location;

    @PrePersist
    public void prePersist() {
        startAt = startAt.withSecond(0).withNano(0);
        endAt = endAt.withSecond(0).withNano(0);
    }
}
