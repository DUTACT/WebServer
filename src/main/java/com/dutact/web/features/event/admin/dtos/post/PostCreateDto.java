package com.dutact.web.features.event.admin.dtos.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PostCreateDto {
    @JsonProperty("eventId")
    private Integer eventId;

    @JsonProperty("content")
    private String content;

    @JsonProperty("coverPhotoUrl")
    private String coverPhotoUrl;
}
