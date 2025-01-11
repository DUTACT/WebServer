package com.dutact.web.dto.post.admin;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostUpdateDtoV1 {
    @Nullable
    private String content;

    @Nullable
    private MultipartFile coverPhoto;
}
