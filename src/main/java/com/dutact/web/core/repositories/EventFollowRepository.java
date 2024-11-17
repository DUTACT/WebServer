package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.EventFollow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventFollowRepository extends JpaRepository<EventFollow, Integer>, JpaSpecificationExecutor<EventFollow> {
    boolean existsByEventIdAndStudentId(Integer eventId, Integer studentId);

    void deleteByEventIdAndStudentId(Integer eventId, Integer studentId);
    
    List<EventFollow> findAllByStudentId(Integer studentId);

    Page<EventFollow> findAllByStudentId(Integer studentId, Pageable pageable);

    @Query("SELECT COUNT(ef) FROM EventFollow ef WHERE ef.event.id = :eventId")
    Integer countByEventId(Integer eventId);
}
