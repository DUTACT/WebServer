package com.dutact.web.features.analytics.admin.dtos.registration;

import com.dutact.web.common.constants.GlobalTimeConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class EventRegistrationQueryParams {
    @Nullable
    @Min(-12)
    @Max(14)
    @Schema(description = "Timezone offset in hours", defaultValue = "7")
    private Integer timezoneOffset = GlobalTimeConfig.DEFAULT_TIME_ZONE_OFFSET;
}
