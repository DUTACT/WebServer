package com.dutact.web.features.profile.admin.service;

import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.profile.admin.dtos.StudentProfileUpdateDto;

public interface StudentProfileService {
    StudentProfileDto getProfile(Integer studentId) throws NotExistsException;
    void updateProfile(Integer studentId, StudentProfileUpdateDto studentProfileDto) throws NotExistsException, ConflictException;
}
