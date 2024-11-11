package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.eventregistration.EventRegistration;
import com.dutact.web.core.projections.RegistrationCountByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Integer>, JpaSpecificationExecutor<EventRegistration> {
    boolean existsByEventIdAndStudentId(Integer eventId, Integer studentId);

    void deleteByEventIdAndStudentId(Integer eventId, Integer studentId);

    @Query("SELECT FUNCTION('DATE_TRUNC', 'day', er.registeredAt) as date, COUNT(er) as count " +
            "FROM EventRegistration er " +
            "WHERE er.event.id = :eventId " +
            "GROUP BY FUNCTION('DATE_TRUNC', 'day', er.registeredAt)")
    List<RegistrationCountByDate> countRegistrationByDate(Integer eventId);
}
