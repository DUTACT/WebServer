package com.dutact.web.features.event.admin.dtos.event;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class EventCreateDto {
    @NotNull
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
