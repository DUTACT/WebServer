package com.dutact.web.features.checkin.admin.dtos;

import com.dutact.web.core.entities.checkincode.CheckInLocation;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateEventCheckInCodeDto {
    @NotNull
    private Integer eventId;

    @NotNull
    private String title;

    @NotNull
    private LocalDateTime startAt;

    @NotNull
    private LocalDateTime endAt;

    @Nullable
    private CheckInLocation location;
}
