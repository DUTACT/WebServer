package com.dutact.web.features.checkin.admin.dtos;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class GetParticipationQueryParam {
    private Integer eventId;

    @Nullable
    private String searchQuery;

    private Integer page = 0;

    private Integer pageSize = 20;
}
