package com.dutact.web.features.analytics.admin.dtos.organizer;

import lombok.Data;
import lombok.Value;

@Value
@Data
public class OrganizerOverallStatsDto {
    long totalEvents;
    long totalFollows;
    long totalPostLikes;
    long totalFeedbacks;
    long totalFeedbackLikes;
} 