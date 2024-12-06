package com.dutact.web.features.statistics.services;

import com.dutact.web.features.statistics.dto.DailyActivityStatsDto;
import com.dutact.web.features.statistics.dto.MonthlyActivityStatsDto;
import com.dutact.web.core.repositories.DateCountProjection;
import com.dutact.web.core.repositories.EventActivityStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventActivityStatsService {
    private final EventActivityStatsRepository statsRepository;

    public MonthlyActivityStatsDto getMonthlyActivityStats(Integer eventId) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(1);
        
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        // Get all stats
        List<DateCountProjection> followers = statsRepository.getFollowerStats(eventId, startDateTime, endDateTime);
        List<DateCountProjection> postLikes = statsRepository.getPostLikeStats(eventId, startDateTime, endDateTime);
        List<DateCountProjection> feedbackLikes = statsRepository.getFeedbackLikeStats(eventId, startDateTime, endDateTime);
        List<DateCountProjection> registrations = statsRepository.getRegistrationStats(eventId, startDateTime, endDateTime);

        // Convert to maps for easier access
        Map<LocalDate, Long> followerMap = toMap(followers);
        Map<LocalDate, Long> postLikeMap = toMap(postLikes);
        Map<LocalDate, Long> feedbackLikeMap = toMap(feedbackLikes);
        Map<LocalDate, Long> registrationMap = toMap(registrations);

        // Create daily stats for each date in the range
        List<DailyActivityStatsDto> dailyStats = new ArrayList<>();
        LocalDate currentDate = startDate;
        
        while (!currentDate.isAfter(endDate)) {
            dailyStats.add(DailyActivityStatsDto.builder()
                .date(currentDate)
                .followers(followerMap.getOrDefault(currentDate, 0L))
                .postLikes(postLikeMap.getOrDefault(currentDate, 0L))
                .feedbackLikes(feedbackLikeMap.getOrDefault(currentDate, 0L))
                .registrations(registrationMap.getOrDefault(currentDate, 0L))
                .build());
                
            currentDate = currentDate.plusDays(1);
        }

        return MonthlyActivityStatsDto.builder()
            .dailyStats(dailyStats)
            .build();
    }

    private Map<LocalDate, Long> toMap(List<DateCountProjection> projections) {
        Map<LocalDate, Long> map = new HashMap<>();
        for (DateCountProjection projection : projections) {
            map.put(projection.getDate(), projection.getCount());
        }
        return map;
    }
} 