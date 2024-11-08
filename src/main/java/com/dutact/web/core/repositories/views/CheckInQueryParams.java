package com.dutact.web.core.repositories.views;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class CheckInQueryParams {
    @Nonnull
    private Integer eventId;

    @Nullable
    private String searchQuery;

    @Nonnull
    private Integer page = 0;

    @Nonnull
    private Integer pageSize = 20;
}
