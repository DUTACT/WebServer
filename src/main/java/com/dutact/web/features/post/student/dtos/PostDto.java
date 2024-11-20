package com.dutact.web.features.post.student.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "StudentPostDetailsDto")
public class PostDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("content")
    private String content;

    @JsonProperty("likeNumber")
    private Integer likeNumber;

    @JsonProperty("postedAt")
    private LocalDateTime postedAt;

    @Nullable
    @JsonProperty("likedAt")
    private LocalDateTime likedAt;

    @JsonProperty("coverPhotoUrl")
    private String coverPhotoUrl;

    @JsonProperty("event")
    private PostEventDto event;
}
