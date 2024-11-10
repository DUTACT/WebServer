package com.dutact.web.core.repositories.participation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

@JsonSubTypes({
        @JsonSubTypes.Type(value = ConfirmParticipationCondition.All.class, name = ConfirmParticipationCondition.All.TYPE_NAME),
        @JsonSubTypes.Type(value = ConfirmParticipationCondition.WithStudentsIds.class, name = ConfirmParticipationCondition.WithStudentsIds.TYPE_NAME),
        @JsonSubTypes.Type(value = ConfirmParticipationCondition.CheckedInAtLeast.class, name = ConfirmParticipationCondition.CheckedInAtLeast.TYPE_NAME)
})
public abstract class ConfirmParticipationCondition {
    public static class All extends ConfirmParticipationCondition {
        public static final String TYPE_NAME = "all";
    }

    @Getter
    @Setter
    public static class WithStudentsIds extends ConfirmParticipationCondition {
        public static final String TYPE_NAME = "withStudentsIds";

        @Nonnull
        private String[] studentsIds = new String[0];
    }

    @Getter
    @Setter
    public static class CheckedInAtLeast extends ConfirmParticipationCondition {
        public static final String TYPE_NAME = "checkedInAtLeast";

        private int count = 0;
    }
}
