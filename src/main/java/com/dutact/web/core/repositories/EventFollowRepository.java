package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.EventFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventFollowRepository extends JpaRepository<EventFollow, Integer>, JpaSpecificationExecutor<EventFollow> {
    boolean existsByEventIdAndStudentId(Integer eventId, Integer studentId);

    Optional<EventFollow> findByStudentIdAndEventId(Integer studentId, Integer eventId);

    void deleteByEventIdAndStudentId(Integer eventId, Integer studentId);

    List<EventFollow> findAllByStudentId(Integer studentId);
    
    @Query("SELECT COUNT(ef) FROM EventFollow ef WHERE ef.event.id = :eventId")
    Integer countByEventId(Integer eventId);

    @Query("SELECT ef.student.id FROM EventFollow ef WHERE ef.event.id = :eventId")
    Collection<Integer> findStudentIdsByEventId(Integer eventId);
}
