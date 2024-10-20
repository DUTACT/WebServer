package com.dutact.web.features.feedback.student.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "StudentFeedbackDto")
public class FeedbackDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("content")
    private String content;

    @JsonProperty("postedAt")
    private LocalDateTime postedAt;

    @JsonProperty("coverPhotoUrl")
    private String coverPhotoUrl;

    @JsonProperty("student")
    private FeedbackStudentDto student;

    @JsonProperty("event")
    private FeedbackEventDto event;
}
