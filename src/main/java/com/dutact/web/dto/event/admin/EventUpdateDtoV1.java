package com.dutact.web.dto.event.admin;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class EventUpdateDtoV1 {
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

    @NotNull
    private MultipartFile coverPhoto;
}
