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
        @JsonSubTypes.Type(value = RejectParticipationCriterion.All.class, name = RejectParticipationCriterion.All.TYPE_NAME),
        @JsonSubTypes.Type(value = RejectParticipationCriterion.WithStudentsIds.class, name = RejectParticipationCriterion.WithStudentsIds.TYPE_NAME)
})
public abstract class RejectParticipationCriterion {
    @Nonnull
    private String reason;

    @NoArgsConstructor
    public static class All extends RejectParticipationCriterion {
        public static final String TYPE_NAME = "all";
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class WithStudentsIds extends RejectParticipationCriterion {
        public static final String TYPE_NAME = "withStudentsIds";

        @Nonnull
        private Integer[] studentsIds = new Integer[0];
    }
}
