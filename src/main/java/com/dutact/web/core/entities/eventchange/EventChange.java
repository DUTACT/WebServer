package com.dutact.web.core.entities.eventchange;

import com.dutact.web.core.entities.eventchange.details.EventChangeDetails;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_change")
@Getter
@Setter
public class EventChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "details", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = EventChangeDetailsConverter.class)
    private EventChangeDetails details;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @PrePersist
    public void prePersist() {
        if (changedAt == null) {
            changedAt = LocalDateTime.now();
        }
    }
}
