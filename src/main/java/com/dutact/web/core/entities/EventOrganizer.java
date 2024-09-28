package com.dutact.web.core.entities;

import com.dutact.web.auth.factors.Account;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "event_organizer")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventOrganizer extends Account {
    private String name;
    private String avatar_url;
}
