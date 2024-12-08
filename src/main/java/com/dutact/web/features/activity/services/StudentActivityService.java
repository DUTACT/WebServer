package com.dutact.web.features.activity.services;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.core.entities.StudentActivity;
import com.dutact.web.features.activity.dto.ActivityType;
import com.dutact.web.features.activity.dto.StudentActivityDto;

public interface StudentActivityService {
    PageResponse<StudentActivityDto> getStudentActivities(Integer studentId, Integer page, Integer pageSize);
    void recordActivity(Integer studentId, StudentActivity activity);
    void removeActivity(Integer studentId, ActivityType type, Integer eventId);
    void removePostLikeActivity(Integer studentId, Integer postId);
    void removeFeedbackLikeActivity(Integer studentId, Integer feedbackId);
} 