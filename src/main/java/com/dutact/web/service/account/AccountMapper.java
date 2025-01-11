package com.dutact.web.service.account;

import com.dutact.web.data.entity.EventOrganizer;
import com.dutact.web.data.entity.Student;
import com.dutact.web.data.entity.auth.Account;
import com.dutact.web.dto.account.AccountDto;
import com.dutact.web.dto.account.CreateOrganizerAccountDto;
import com.dutact.web.dto.account.OrganizerAccountDto;
import com.dutact.web.dto.account.StudentAccountDto;
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
