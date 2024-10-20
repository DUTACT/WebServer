package com.dutact.web.features.post.student.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "StudentPostEventDetailsDto")
public class PostEventDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;
}
