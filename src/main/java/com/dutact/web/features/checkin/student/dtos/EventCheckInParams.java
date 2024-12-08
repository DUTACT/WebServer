package com.dutact.web.features.checkin.student.dtos;

import com.dutact.web.core.entities.checkincode.GeoPosition;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class EventCheckInParams {
    @Nonnull
    private String code;

    @Nonnull
    private Integer studentId;

    @Nullable
    private GeoPosition geoPosition;
}
