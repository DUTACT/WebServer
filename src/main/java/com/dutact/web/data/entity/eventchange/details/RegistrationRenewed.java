package com.dutact.web.data.entity.eventchange.details;

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
public class RegistrationRenewed extends EventChangeDetails {
    public static final String TYPE_NAME = "registrationRenewed";

    @Nonnull
    private LocalDateTime newEndRegistration;

    @Nonnull
    private LocalDateTime oldEndRegistration;
}
