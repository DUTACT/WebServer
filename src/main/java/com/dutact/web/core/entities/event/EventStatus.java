package com.dutact.web.core.entities.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EventStatus.Approved.class, name = EventStatus.Approved.TYPE_NAME),
        @JsonSubTypes.Type(value = EventStatus.Pending.class, name = EventStatus.Pending.TYPE_NAME),
        @JsonSubTypes.Type(value = EventStatus.Rejected.class, name = EventStatus.Rejected.TYPE_NAME)
})
public abstract class EventStatus {
    public static Approved approved() {
        return new Approved();
    }

    public static Pending pending() {
        return new Pending();
    }

    public static Rejected rejected(String reason) {
        Rejected rejected = new Rejected();
        rejected.setReason(reason);
        return rejected;
    }

    public static class Approved extends EventStatus {
        public static final String TYPE_NAME = "approved";
    }

    public static class Pending extends EventStatus {
        public static final String TYPE_NAME = "pending";
    }

    @Getter
    @Setter
    public static class Rejected extends EventStatus {
        public static final String TYPE_NAME = "rejected";

        private String reason;
    }
}
