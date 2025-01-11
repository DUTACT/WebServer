package com.dutact.web.dto.feedback.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(name = "StudentFeedbackManagementDto")
public class FeedbackDto {
    private Integer id;
    private String postedAt;
    private String content;
    private String coverPhotoUrl;
    private List<String> coverPhotoUrls;
    private String studentName;
    private Integer studentId;
    private String studentAvatarUrl;
}
