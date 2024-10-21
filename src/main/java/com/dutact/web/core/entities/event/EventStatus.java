package com.dutact.web.core.entities.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EventStatus.Approved.class, name = EventStatus.Approved.TYPE_NAME),
        @JsonSubTypes.Type(value = EventStatus.Pending.class, name = EventStatus.Pending.TYPE_NAME),
        @JsonSubTypes.Type(value = EventStatus.Rejected.class, name = EventStatus.Rejected.TYPE_NAME)
})
public abstract class EventStatus {
    @Getter
    @Setter
    public static class Approved extends EventStatus {
        public static final String TYPE_NAME = "approved";
        public String moderatedAt;
        public Approved() {
            this.moderatedAt = LocalDateTime.now().toString();
        }
    }

    @NoArgsConstructor
    public static class Pending extends EventStatus {
        public static final String TYPE_NAME = "pending";
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Rejected extends EventStatus {
        public static final String TYPE_NAME = "rejected";
        private String reason;
        private String moderatedAt;
        public Rejected(String reason) {
            this.reason = reason;
            this.moderatedAt = LocalDateTime.now().toString();
        }
    }
}
