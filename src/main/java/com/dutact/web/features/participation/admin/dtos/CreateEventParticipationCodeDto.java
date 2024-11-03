package com.dutact.web.features.participation.admin.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateEventParticipationCodeDto {
    @NotNull
    private LocalDateTime startAt;

    @NotNull
    private LocalDateTime endAt;
}
