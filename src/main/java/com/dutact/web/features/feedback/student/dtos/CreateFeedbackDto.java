package com.dutact.web.features.feedback.student.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateFeedbackDto {
    @NotNull
    private Integer eventId;

    @NotNull
    private String content;

    @Nullable
    private MultipartFile coverPhoto;
}
