package com.dutact.web.data.repository;

import com.dutact.web.data.entity.EventFollow;
import com.dutact.web.data.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT COUNT(ef) FROM EventFollow ef WHERE ef.event.organizer.id = :organizerId")
    long countByOrganizerId(@Param("organizerId") Integer organizerId);

    @Query("SELECT ef.student FROM EventFollow ef WHERE ef.event.id = :eventId")
    List<Student> findStudentsByEventId(@Param("eventId") Integer eventId);
}
