package com.dutact.web.dto.post.admin;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostCreateDtoV2 {
    @NotNull
    private Integer eventId;

    @NotNull
    private String content;

    @NotNull
    private List<MultipartFile> coverPhotos;
}
