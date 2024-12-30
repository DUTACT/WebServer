package com.dutact.web.features.feedback.student.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Data
@Schema(name = "StudentFeedbackDetailsDto")
public class FeedbackDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("content")
    private String content;

    @JsonProperty("likedNumber")
    private Integer likeNumber;

    @JsonProperty("postedAt")
    private LocalDateTime postedAt;

    @Nullable
    @JsonProperty("likedAt")
    private LocalDateTime likedAt;

    @JsonProperty("coverPhotoUrl")
    private String coverPhotoUrl;

    @JsonProperty("event")
    private FeedbackEventDto event;

    @JsonProperty("student")
    private FeedbackStudentDto student;
}
