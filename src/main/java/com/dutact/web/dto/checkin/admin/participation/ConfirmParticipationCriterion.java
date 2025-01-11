package com.dutact.web.dto.checkin.admin.participation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ConfirmParticipationCriterion.All.class, name = ConfirmParticipationCriterion.All.TYPE_NAME),
        @JsonSubTypes.Type(value = ConfirmParticipationCriterion.WithStudentsIds.class, name = ConfirmParticipationCriterion.WithStudentsIds.TYPE_NAME),
        @JsonSubTypes.Type(value = ConfirmParticipationCriterion.CheckedInAtLeast.class, name = ConfirmParticipationCriterion.CheckedInAtLeast.TYPE_NAME)
})
public abstract class ConfirmParticipationCriterion {
    @NoArgsConstructor
    public static class All extends ConfirmParticipationCriterion {
        public static final String TYPE_NAME = "all";
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class WithStudentsIds extends ConfirmParticipationCriterion {
        public static final String TYPE_NAME = "withStudentsIds";

        @Nonnull
        private Integer[] studentsIds = new Integer[0];
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CheckedInAtLeast extends ConfirmParticipationCriterion {
        public static final String TYPE_NAME = "checkedInAtLeast";

        private int count = 0;
    }
}
