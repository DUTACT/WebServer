package com.dutact.web.core.entities.eventchange.details;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationClosed extends EventChangeDetails {
    public static final String TYPE_NAME = "registrationClosed";

    @Nonnull
    private LocalDateTime newEndRegistration;

    @Nonnull
    private LocalDateTime oldEndRegistration;
}
