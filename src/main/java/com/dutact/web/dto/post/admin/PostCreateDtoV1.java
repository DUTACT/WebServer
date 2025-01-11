package com.dutact.web.dto.post.admin;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PostCreateDtoV1 {
    @NotNull
    private Integer eventId;

    @NotNull
    private String content;

    @NotNull
    private MultipartFile coverPhoto;
}
