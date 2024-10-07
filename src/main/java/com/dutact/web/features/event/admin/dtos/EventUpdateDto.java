package com.dutact.web.features.event.admin.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class EventUpdateDto {
    @Nullable
    private String name;

    @NotNull
    private String content;

    @NotNull
    private LocalDateTime startAt;

    @NotNull
    private LocalDateTime endAt;

    @NotNull
    private LocalDateTime startRegistrationAt;

    @NotNull
    private LocalDateTime endRegistrationAt;

    @NotNull
    private MultipartFile coverPhoto;
}
