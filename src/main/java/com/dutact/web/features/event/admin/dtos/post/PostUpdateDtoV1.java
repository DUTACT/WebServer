package com.dutact.web.features.event.admin.dtos.post;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostUpdateDtoV1 {
    @Nullable
    private String content;

    @Nullable
    private MultipartFile coverPhoto;
}
