package com.dutact.web.features.profile.admin.service;

import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.profile.admin.dtos.OrganizerProfileDto;
import com.dutact.web.features.profile.admin.dtos.OrganizerProfileUpdateDto;

public interface OrganizerProfileService {
    OrganizerProfileDto getProfile(Integer id) throws NotExistsException;
    OrganizerProfileDto updateProfile(Integer id, OrganizerProfileUpdateDto organizerProfileUpdateDto) throws NotExistsException, ConflictException;
}
