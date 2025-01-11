package com.dutact.web.dto.post.admin;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PostUpdateDtoV2 {
    @Nullable
    private String content;

    @NotNull
    private List<MultipartFile> coverPhotos;

    @Nullable
    private List<String> keepCoverPhotoUrls;
}
