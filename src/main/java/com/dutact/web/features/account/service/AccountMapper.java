package com.dutact.web.features.account.service;

import com.dutact.web.auth.factors.Account;
import com.dutact.web.core.entities.EventOrganizer;
import com.dutact.web.core.entities.event.Event;
import com.dutact.web.features.account.dto.AccountDto;
import com.dutact.web.features.account.dto.CreateOrganizerAccountDto;
import com.dutact.web.features.event.admin.dtos.event.EventCreateDto;
import com.dutact.web.features.event.admin.dtos.event.EventDto;
import com.dutact.web.features.event.admin.dtos.event.EventUpdateDto;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {
    AccountDto toAccountDto(Account account);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    EventOrganizer toOrganizer(CreateOrganizerAccountDto eventDto);
}
