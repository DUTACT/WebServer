package com.dutact.web.core.entities;

import com.dutact.web.auth.factors.Account;
import com.dutact.web.core.entities.event.Event;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event_organizer")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventOrganizer extends Account {
    private String name;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Builder.Default
    @OneToMany(mappedBy = "organizer")
    private List<Event> events = new ArrayList<>();

    public EventOrganizer(Integer id) {
        super(id);
    }
}
