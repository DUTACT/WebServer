package com.dutact.web.features.event.admin.dtos.post;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostUpdateDto {
    @Nullable
    private String content;

    @Nullable
    private MultipartFile coverPhoto;
}
