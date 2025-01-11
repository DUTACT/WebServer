package com.dutact.web.controller.checkin.student;

import com.dutact.web.data.entity.checkincode.GeoPosition;
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
