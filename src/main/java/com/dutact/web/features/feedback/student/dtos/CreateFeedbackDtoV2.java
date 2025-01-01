package com.dutact.web.features.feedback.student.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CreateFeedbackDtoV2 {
    @NotNull
    private Integer eventId;

    @NotNull
    private String content;

    @Nullable
    private List<MultipartFile> coverPhotos;
}
