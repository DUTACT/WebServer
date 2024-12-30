package com.dutact.web.features.feedback.admin.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "StudentFeedbackManagementDto")
public class FeedbackDto {
    private Integer id;
    private String postedAt;
    private String content;
    private String coverPhotoUrl;
    private String studentName;
    private Integer studentId;
    private String studentAvatarUrl;
}
