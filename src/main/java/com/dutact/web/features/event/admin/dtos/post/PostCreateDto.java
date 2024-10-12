package com.dutact.web.features.event.admin.dtos.post;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostCreateDto {
    @NotNull
    private Integer eventId;

    @NotNull
    private String content;

    @NotNull
    private MultipartFile coverPhoto;
}
