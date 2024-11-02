package com.dutact.web.features.profile.student.service;

import com.dutact.web.common.api.exceptions.ConflictException;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.profile.student.dtos.StudentProfileDto;
import com.dutact.web.features.profile.student.dtos.StudentProfileUpdateDto;

public interface StudentProfileService {
    StudentProfileDto getProfile(Integer studentId);
    void updateProfile(Integer studentId, StudentProfileUpdateDto studentProfileDto) throws NotExistsException, ConflictException;
}
