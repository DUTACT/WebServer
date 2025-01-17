package com.dutact.web.dto.feedback.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
@Schema(name = "StudentFeedbackStudentDto")
public class FeedbackStudentDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String fullName;

    @Nullable
    @JsonProperty("avatarUrl")
    private String avatarUrl;
}
