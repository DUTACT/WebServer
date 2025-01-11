package com.dutact.web.service.profile.admin;

import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.dto.auth.NewPasswordDto;
import com.dutact.web.dto.profile.admin.OrganizerProfileDto;
import com.dutact.web.dto.profile.admin.OrganizerProfileUpdateDto;
import com.dutact.web.service.auth.exception.InvalidCredentialsException;
import com.dutact.web.service.auth.exception.NoPermissionException;

public interface OrganizerProfileService {
    OrganizerProfileDto getProfile(Integer id) throws NotExistsException;

    OrganizerProfileDto updateProfile(Integer id, OrganizerProfileUpdateDto organizerProfileUpdateDto) throws NotExistsException, ConflictException;

    void changePassword(Integer id, NewPasswordDto newPasswordDto) throws NotExistsException, NoPermissionException, InvalidCredentialsException;
}
