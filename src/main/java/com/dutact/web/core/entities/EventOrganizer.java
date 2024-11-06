package com.dutact.web.core.entities;

import com.dutact.web.auth.factors.Account;
import com.dutact.web.core.entities.common.UploadFileConverter;
import com.dutact.web.core.entities.common.UploadedFile;
import com.dutact.web.core.entities.event.Event;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "event_organizer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventOrganizer extends Account {
    @Column(name = "name")
    private String name;

    @Nullable
    @Column(name = "avatar", columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = UploadFileConverter.class)
    private UploadedFile avatar;

    @Nullable
    @Column(name = "phone")
    private String phone;

    @Nullable
    @Column(name = "address")
    private String address;

    @Nullable
    @Column(name = "person_in_charge_name")
    private String personInChargeName;

    @OneToMany(mappedBy = "organizer")
    private List<Event> events = new ArrayList<>();
}
