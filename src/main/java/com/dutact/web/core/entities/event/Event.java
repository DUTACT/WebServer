package com.dutact.web.core.entities.event;

import com.dutact.web.core.entities.EventOrganizer;
import com.dutact.web.core.entities.common.UploadFileConverter;
import com.dutact.web.core.entities.common.UploadedFile;
import com.dutact.web.core.entities.post.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event")
@Getter
@Setter
public class Event {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @Column(name = "cover_photo", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = UploadFileConverter.class)
    private UploadedFile coverPhoto;

    @Column(name = "status", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = EventStatusConverter.class)
    private EventStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private EventOrganizer organizer;

    @OneToMany(mappedBy = "event")
    private List<Post> posts = new ArrayList<>();
}
