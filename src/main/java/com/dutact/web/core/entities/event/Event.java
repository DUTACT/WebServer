package com.dutact.web.core.entities.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin")
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "start_registration_at")
    private LocalDateTime startRegistrationAt;

    @Column(name = "end_registration_at")
    private LocalDateTime endRegistrationAt;

    @Column(name = "cover_photo_url")
    private String coverPhotoUrl;

    @Column(name = "status")
    @Convert(converter = EventStatusConverter.class)
    private EventStatus status;
}
