package com.dutact.web.features.feedback.student.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UpdateFeedbackDtoV1 {
    @Nullable
    private String content;

    @Nullable
    private MultipartFile coverPhoto;

    @Schema(description = "If true, the cover photo will be deleted," +
            "if not set, default value is false")
    private boolean deleteCoverPhoto = false;
}
