package com.dutact.web.features.notification.core.timer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ScheduledJobRepository
        extends JpaRepository<ScheduledJob, Integer> {
    Collection<ScheduledJob> getAllByFireAtBefore(LocalDateTime time);

    void deleteAllByIdIn(Collection<Integer> ids);
}
