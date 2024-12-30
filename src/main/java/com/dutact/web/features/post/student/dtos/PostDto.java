package com.dutact.web.features.post.student.dtos;

import com.dutact.web.features.event.student.dtos.OrganizerDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(name = "StudentPostDetailsDto")
public class PostDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("content")
    private String content;

    @JsonProperty("likedNumber")
    private Integer likeNumber;

    @JsonProperty("postedAt")
    private LocalDateTime postedAt;

    @Nullable
    @JsonProperty("likedAt")
    private LocalDateTime likedAt;

    @JsonProperty("coverPhotoUrls")
    private List<String> coverPhotoUrls;

    @JsonProperty("event")
    private PostEventDto event;

    @JsonProperty("organizer")
    private OrganizerDto organizer;
}
