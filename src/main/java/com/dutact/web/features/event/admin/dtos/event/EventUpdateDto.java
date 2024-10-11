package com.dutact.web.features.event.admin.dtos.event;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class EventUpdateDto {
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
    private MultipartFile coverPhoto;
}
