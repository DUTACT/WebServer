package com.dutact.web.dto.feedback.student;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class FeedbackQueryParams {
    @Nullable
    private Integer eventId;

    @Nullable
    private Integer studentId;
}
