package com.dutact.web.features.analytics.admin.dtos.registration;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class EventRegistrationQueryParams {
    @Nullable
    private Integer organizerId;
    
    private Integer eventId;
}
