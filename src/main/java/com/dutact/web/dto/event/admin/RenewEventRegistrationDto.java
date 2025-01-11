package com.dutact.web.dto.event.admin;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RenewEventRegistrationDto {
    @NotNull
    private LocalDateTime endRegistrationAt;
}
