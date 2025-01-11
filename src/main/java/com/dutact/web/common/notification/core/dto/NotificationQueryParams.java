package com.dutact.web.common.notification.core.dto;

import lombok.Data;

@Data
public class NotificationQueryParams {
    private Integer accountId;
    private Integer page = 1;
    private Integer size = 10;

    public void setPage(Integer page) {
        if (page == null || page < 1) {
            throw new IllegalArgumentException("Page number must be greater than 0");
        }

        this.page = page;
    }

    public void setSize(Integer size) {
        if (size == null || size < 1) {
            throw new IllegalArgumentException("Page size must be greater than 0");
        }

        this.size = size;
    }
}
