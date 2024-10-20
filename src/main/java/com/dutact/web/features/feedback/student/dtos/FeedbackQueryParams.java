package com.dutact.web.features.feedback.student.dtos;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class FeedbackQueryParams {
    @Nullable
    private Integer eventId;

    @Nullable
    private Integer studentId;
}
