package com.dutact.web.features.statistics.repositories;

import com.dutact.web.core.entities.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventActivityStatsRepository extends JpaRepository<Event, Integer> {
    
    @Query(value = """
        SELECT 
            DATE(followed_at) as date,
            COUNT(*) as count
        FROM event_follow
        WHERE event_id = :eventId 
        AND followed_at BETWEEN :startDate AND :endDate
        GROUP BY DATE(followed_at)
        ORDER BY date DESC
        """, nativeQuery = true)
    List<DateCountProjection> getFollowerStats(
        @Param("eventId") Integer eventId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query(value = """
        SELECT 
            DATE(liked_at) as date,
            COUNT(*) as count
        FROM post_like pl
        JOIN post p ON p.id = pl.post_id
        WHERE p.event_id = :eventId 
        AND pl.liked_at BETWEEN :startDate AND :endDate
        GROUP BY DATE(liked_at)
        ORDER BY date DESC
        """, nativeQuery = true)
    List<DateCountProjection> getPostLikeStats(
        @Param("eventId") Integer eventId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query(value = """
        SELECT 
            DATE(liked_at) as date,
            COUNT(*) as count
        FROM feedback_like fl
        JOIN feedback f ON f.id = fl.feedback_id
        WHERE f.event_id = :eventId 
        AND fl.liked_at BETWEEN :startDate AND :endDate
        GROUP BY DATE(liked_at)
        ORDER BY date DESC
        """, nativeQuery = true)
    List<DateCountProjection> getFeedbackLikeStats(
        @Param("eventId") Integer eventId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query(value = """
        SELECT 
            DATE(registered_at) as date,
            COUNT(*) as count
        FROM event_registration
        WHERE event_id = :eventId 
        AND registered_at BETWEEN :startDate AND :endDate
        GROUP BY DATE(registered_at)
        ORDER BY date DESC
        """, nativeQuery = true)
    List<DateCountProjection> getRegistrationStats(
        @Param("eventId") Integer eventId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
} 