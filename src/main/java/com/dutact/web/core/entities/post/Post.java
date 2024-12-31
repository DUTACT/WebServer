package com.dutact.web.core.entities.post;

import com.dutact.web.core.entities.common.UploadFileConverter;
import com.dutact.web.core.entities.common.UploadedFile;
import com.dutact.web.core.entities.common.UploadedFileListConverter;
import com.dutact.web.core.entities.event.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "posted_at")
    private LocalDateTime postedAt;

    @Column(name = "cover_photos", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = UploadedFileListConverter.class)
    private List<UploadedFile> coverPhotos = new ArrayList<>();

    @Column(name = "status", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = PostStatusConverter.class)
    private PostStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @PrePersist
    public void prePersist() {
        postedAt = LocalDateTime.now();
    }
}
