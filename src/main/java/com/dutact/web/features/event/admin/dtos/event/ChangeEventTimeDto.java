package com.dutact.web.features.event.admin.dtos.event;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChangeEventTimeDto {
    @Nullable
    private LocalDateTime startAt;

    @Nullable
    private LocalDateTime endAt;

    @Nullable
    private LocalDateTime startRegistrationAt;

    @Nullable
    private LocalDateTime endRegistrationAt;
}
