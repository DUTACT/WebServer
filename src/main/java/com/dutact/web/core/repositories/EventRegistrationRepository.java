package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.Student;
import com.dutact.web.core.entities.eventregistration.EventRegistration;
import com.dutact.web.core.projections.RegistrationCountByDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Integer>, JpaSpecificationExecutor<EventRegistration> {
    boolean existsByEventIdAndStudentId(Integer eventId, Integer studentId);

    void deleteByEventIdAndStudentId(Integer eventId, Integer studentId);

    @Query("SELECT FUNCTION('DATE_TRUNC', 'day', er.registeredAt) as date, COUNT(er) as count " +
            "FROM EventRegistration er " +
            "WHERE er.event.id = :eventId " +
            "GROUP BY FUNCTION('DATE_TRUNC', 'day', er.registeredAt)")
    List<RegistrationCountByDate> countRegistrationByDate(Integer eventId);

    Optional<EventRegistration> findByEventIdAndStudentId(Integer eventId, Integer studentId);

    List<EventRegistration> findAllByStudentId(Integer studentId);

    @Query("SELECT COUNT(er) FROM EventRegistration er WHERE er.event.id = :eventId")
    Integer countByEventId(Integer eventId);

    @Query("SELECT er.student.id FROM EventRegistration er WHERE er.event.id = :eventId")
    List<Integer> findStudentIdsByEventId(Integer eventId);
    
    @Query("SELECT FUNCTION('DATE_TRUNC', 'day', er.registeredAt) as date, COUNT(er) as count " +
            "FROM EventRegistration er " +
            "WHERE er.event.id = :eventId " +
            "AND er.registeredAt BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('DATE_TRUNC', 'day', er.registeredAt)")
    List<RegistrationCountByDate> countRegistrationByDateBetween(
            Integer eventId, 
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = "SELECT DATE_TRUNC('day', er.registered_at) as date, COUNT(er.*) as count " +
            "FROM event_registration er " +
            "WHERE er.event_id = :eventId " +
            "AND er.registered_at BETWEEN :startDate AND :endDate " +
            "AND er.certificate_status->>'type' = :certificateStatus " +
            "GROUP BY DATE_TRUNC('day', er.registered_at)", nativeQuery = true)
    List<RegistrationCountByDate> countRegistrationByDateBetween(
            @Param("eventId") Integer eventId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("certificateStatus") String certificateStatus);

    @Query(value = """
            SELECT DATE_TRUNC('day', er.registered_at) as date, COUNT(er.*) as count 
            FROM event_registration er 
            JOIN event e ON er.event_id = e.id 
            WHERE e.organizer_id = :organizerId 
            AND er.registered_at BETWEEN :startDate AND :endDate 
            GROUP BY DATE_TRUNC('day', er.registered_at)
            ORDER BY date DESC""", nativeQuery = true)
    List<RegistrationCountByDate> countOrganizerRegistrationsByDateBetween(
            @Param("organizerId") Integer organizerId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query(value = """
            SELECT DATE_TRUNC('day', er.registered_at) as date, COUNT(er.*) as count 
            FROM event_registration er 
            JOIN event e ON er.event_id = e.id 
            WHERE e.organizer_id = :organizerId 
            AND er.registered_at BETWEEN :startDate AND :endDate 
            AND er.certificate_status->>'type' = :status 
            GROUP BY DATE_TRUNC('day', er.registered_at)
            ORDER BY date DESC""", nativeQuery = true)
    List<RegistrationCountByDate> countOrganizerParticipationsByDateBetween(
            @Param("organizerId") Integer organizerId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("status") String status);

    @Query("SELECT er.student FROM EventRegistration er WHERE er.event.id = :eventId")
    List<Student> findStudentsByEventId(@Param("eventId") Integer eventId);
}
