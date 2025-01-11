package com.dutact.web.dto.event.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "StudentEventOrganizerDto")
public class OrganizerDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("avatarUrl")
    private String avatarUrl;
}
