package com.dutact.web.features.event.admin.dtos.post;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class PostUpdateDto {
    @Nullable
    private String content;

    @Nullable
    private MultipartFile coverPhoto;
}
