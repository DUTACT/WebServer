package com.dutact.web.dto.post.admin;

import com.dutact.web.data.entity.post.PostStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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

    @JsonProperty("coverPhotoUrls")
    private List<String> coverPhotoUrls;

    @JsonProperty("status")
    private PostStatus status;
}
