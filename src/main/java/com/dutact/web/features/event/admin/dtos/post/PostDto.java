package com.dutact.web.features.event.admin.dtos.post;

import com.dutact.web.core.entities.post.PostStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("eventId")
    private Integer eventId;

    @JsonProperty("content")
    private String content;

    @JsonProperty("postedAt")
    private LocalDateTime postedAt;

    @JsonProperty("coverPhotoUrl")
    private String coverPhotoUrl;

    @JsonProperty("status")
    private PostStatus status;
}
