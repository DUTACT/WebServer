package com.dutact.web.core.repositories.participation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonSubTypes({
        @JsonSubTypes.Type(value = RejectParticipationCondition.WithStudentsIds.class, name = RejectParticipationCondition.WithStudentsIds.TYPE_NAME)
})
public abstract class RejectParticipationCondition {
    @Nonnull
    private String reason;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class WithStudentsIds extends RejectParticipationCondition {
        public static final String TYPE_NAME = "withStudentsIds";

        @Nonnull
        private String[] studentsIds = new String[0];
    }
}
