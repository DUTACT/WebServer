package com.dutact.web.features.account.service;

import com.dutact.web.auth.factors.Account;
import com.dutact.web.core.entities.EventOrganizer;
import com.dutact.web.core.entities.Student;
import com.dutact.web.features.account.dto.AccountDto;
import com.dutact.web.features.account.dto.CreateOrganizerAccountDto;
import com.dutact.web.features.account.dto.OrganizerAccountDto;
import com.dutact.web.features.account.dto.StudentAccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {
    @Mapping(target = "avatarUrl", source = "avatar.fileUrl")
    OrganizerAccountDto toDto(EventOrganizer organizer);

    @Mapping(target = "avatarUrl", source = "avatar.fileUrl")
    StudentAccountDto toDto(Student student);

    AccountDto toDto(Account account);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "events", ignore = true)
    EventOrganizer toOrganizer(CreateOrganizerAccountDto eventDto);
}
