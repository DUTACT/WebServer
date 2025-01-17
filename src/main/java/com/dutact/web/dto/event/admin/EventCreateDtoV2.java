package com.dutact.web.dto.event.admin;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventCreateDtoV2 {
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
    private List<MultipartFile> coverPhotos;
}
