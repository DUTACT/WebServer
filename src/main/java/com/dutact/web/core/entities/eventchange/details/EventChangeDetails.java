package com.dutact.web.core.entities.eventchange.details;

import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonSubTypes({
        @JsonSubTypes.Type(value = RegistrationRenewed.class, name = RegistrationRenewed.TYPE_NAME),
        @JsonSubTypes.Type(value = RegistrationClosed.class, name = RegistrationClosed.TYPE_NAME),
        @JsonSubTypes.Type(value = EventTimeChanged.class, name = EventTimeChanged.TYPE_NAME)
})
public abstract class EventChangeDetails {
}
