package com.dutact.web.features.event.admin.dtos.event;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class EventSearchParams {
    @Nullable
    private String[] statuses;

    @Nullable
    private Integer organizerId;

    @Nonnull
    private Integer page;

    @Nonnull
    private Integer pageSize;

    @Nullable
    private String sort;

    @Nullable
    private String order;
}
