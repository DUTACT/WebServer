package com.dutact.web.features.event.admin.dtos.event;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventUpdateDtoV2 {
    @Nullable
    private String name;

    @Nullable
    private String content;

    @Nullable
    private LocalDateTime startAt;

    @Nullable
    private LocalDateTime endAt;

    @Nullable
    private LocalDateTime startRegistrationAt;

    @Nullable
    private LocalDateTime endRegistrationAt;

    @Nullable
    private List<String> keepCoverPhotoUrls;

    @NotNull
    private List<MultipartFile> coverPhotos;
}
