package com.dutact.web.dto.newsfeed;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "StudentNewsfeedEventDto")
public class NewsfeedEventDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("coverPhotoUrl")
    private String coverPhotoUrl;

    @JsonProperty("coverPhotoUrls")
    private List<String> coverPhotoUrls;
}
