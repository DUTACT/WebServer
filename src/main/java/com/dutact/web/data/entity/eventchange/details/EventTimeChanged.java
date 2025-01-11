package com.dutact.web.data.entity.eventchange.details;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventTimeChanged extends EventChangeDetails {
    public static final String TYPE_NAME = "eventTimeChanged";

    @Nullable
    private StartTimeChange startTimeChange;

    @Nullable
    private EndTimeChange endTimeChange;

    @Nullable
    private RegistrationStartTimeChange registrationStartChange;

    @Nullable
    private RegistrationEndTimeChange registrationEndChange;

    @Data
    @NoArgsConstructor
    public static class StartTimeChange {
        @Nonnull
        private LocalDateTime oldStart;

        @Nonnull
        private LocalDateTime newStart;
    }

    @Data
    @NoArgsConstructor
    public static class EndTimeChange {
        @Nonnull
        private LocalDateTime oldEnd;

        @Nonnull
        private LocalDateTime newEnd;
    }

    @Data
    @NoArgsConstructor
    public static class RegistrationStartTimeChange {
        @Nonnull
        private LocalDateTime oldStartRegistration;

        @Nonnull
        private LocalDateTime newStartRegistration;
    }

    @Data
    @NoArgsConstructor
    public static class RegistrationEndTimeChange {
        @Nonnull
        private LocalDateTime oldEndRegistration;

        @Nonnull
        private LocalDateTime newEndRegistration;
    }
}
