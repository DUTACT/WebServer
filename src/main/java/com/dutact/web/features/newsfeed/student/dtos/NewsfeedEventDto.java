package com.dutact.web.features.newsfeed.student.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "StudentNewsfeedEventDto")
public class NewsfeedEventDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("coverPhotoUrl")
    private String coverPhotoUrl;
}
