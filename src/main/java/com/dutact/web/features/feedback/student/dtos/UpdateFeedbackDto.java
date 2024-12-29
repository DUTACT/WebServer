package com.dutact.web.features.feedback.student.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UpdateFeedbackDto {
    @Nullable
    private String content;

    @NotNull
    private List<MultipartFile> coverPhotos;
}
