package com.dutact.web.data.entity.event;

import com.dutact.web.data.entity.EventOrganizer;
import com.dutact.web.data.entity.common.UploadedFile;
import com.dutact.web.data.entity.common.UploadedFileListConverter;
import com.dutact.web.data.entity.eventregistration.EventRegistration;
import com.dutact.web.data.entity.post.Post;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
public class Event {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "start_at")
    private LocalDateTime startAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    @Column(name = "start_registration_at")
    private LocalDateTime startRegistrationAt;

    @Nonnull
    @Column(name = "end_registration_at")
    private LocalDateTime endRegistrationAt;

    @Column(name = "cover_photos", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = UploadedFileListConverter.class)
    private List<UploadedFile> coverPhotos = new ArrayList<>();

    @Column(name = "status", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = EventStatusConverter.class)
    private EventStatus status = new EventStatus.Pending();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private EventOrganizer organizer;

    @OneToMany(mappedBy = "event")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<EventRegistration> eventRegistrations = new ArrayList<>();

    public Event(Integer id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public void setStatus(EventStatus status) throws CannotChangeStatusException {
        this.status = this.status.changeStatus(status);
    }
}
