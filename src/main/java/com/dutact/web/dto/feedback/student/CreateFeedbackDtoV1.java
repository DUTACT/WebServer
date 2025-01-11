package com.dutact.web.dto.feedback.student;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateFeedbackDtoV1 {
    @NotNull
    private Integer eventId;

    @NotNull
    private String content;

    @Nullable
    private MultipartFile coverPhoto;
}
