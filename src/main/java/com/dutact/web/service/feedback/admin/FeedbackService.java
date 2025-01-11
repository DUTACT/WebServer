package com.dutact.web.service.feedback.admin;

import com.dutact.web.dto.feedback.admin.FeedbackDto;

import java.util.List;

public interface FeedbackService {
    List<FeedbackDto> getFeedbacks(Integer eventId);

    void deleteFeedback(Integer feedbackId);
}
