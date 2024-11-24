package com.dutact.web.features.account.service;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class RoleSpecifiedAccountQueryParams {
    @Nullable
    private String searchQuery;

    @Nonnull
    private Integer page = 1;

    @Nonnull
    private Integer pageSize = 10;

    public void setPage(@Nonnull Integer page) {
        if (page < 1) {
            throw new IllegalArgumentException("Page must be greater than 0");
        }

        this.page = page;
    }

    public void setPageSize(@Nonnull Integer pageSize) {
        if (pageSize < 1) {
            throw new IllegalArgumentException("Page size must be greater than 0");
        }

        this.pageSize = pageSize;
    }
}