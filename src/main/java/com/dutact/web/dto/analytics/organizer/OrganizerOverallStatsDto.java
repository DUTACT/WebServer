package com.dutact.web.dto.analytics.organizer;

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