package com.dutact.web.service.activity;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.dto.activity.ActivityType;
import com.dutact.web.dto.activity.StudentActivityDto;
import com.dutact.web.data.entity.StudentActivity;

public interface StudentActivityService {
    PageResponse<StudentActivityDto> getStudentActivities(Integer studentId, Integer page, Integer pageSize);

    void recordActivity(Integer studentId, StudentActivity activity);

    void removeActivity(Integer studentId, ActivityType type, Integer eventId);

    void removePostLikeActivity(Integer studentId, Integer postId);

    void removeFeedbackLikeActivity(Integer studentId, Integer feedbackId);
} 