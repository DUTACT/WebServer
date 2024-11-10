package com.dutact.web.features.event.admin.dtos.event;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RenewEventRegistrationDto {
    @NotNull
    private LocalDateTime endRegistrationAt;
}
