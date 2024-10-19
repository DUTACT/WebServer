package com.dutact.web.features.feedback.student.dtos;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateFeedbackDto {
    @Nullable
    private String content;

    @Nullable
    private MultipartFile coverPhoto;
}
