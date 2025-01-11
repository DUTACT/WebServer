package com.dutact.web.data.entity.eventchange.details;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RegistrationRenewed.class, name = RegistrationRenewed.TYPE_NAME),
        @JsonSubTypes.Type(value = RegistrationClosed.class, name = RegistrationClosed.TYPE_NAME),
        @JsonSubTypes.Type(value = EventTimeChanged.class, name = EventTimeChanged.TYPE_NAME)
})
public abstract class EventChangeDetails {
}
