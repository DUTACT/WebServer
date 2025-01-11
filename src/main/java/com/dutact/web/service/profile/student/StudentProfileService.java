package com.dutact.web.service.profile.student;

import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.dto.auth.NewPasswordDto;
import com.dutact.web.dto.profile.student.StudentProfileDto;
import com.dutact.web.dto.profile.student.StudentProfileUpdateDto;
import com.dutact.web.service.auth.exception.InvalidCredentialsException;
import com.dutact.web.service.auth.exception.NoPermissionException;

public interface StudentProfileService {
    StudentProfileDto getProfile(Integer studentId) throws NotExistsException;

    StudentProfileDto updateProfile(Integer studentId, StudentProfileUpdateDto studentProfileDto) throws NotExistsException, ConflictException;

    void changePassword(Integer studentId, NewPasswordDto newPasswordDto) throws NotExistsException, NoPermissionException, InvalidCredentialsException;
}
