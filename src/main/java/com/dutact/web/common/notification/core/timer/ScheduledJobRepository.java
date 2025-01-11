package com.dutact.web.common.notification.core.timer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ScheduledJobRepository
        extends JpaRepository<ScheduledJob, Integer> {
    Collection<ScheduledJob> getAllByFireAtBeforeAndExpireAtAfter(LocalDateTime fireAt, LocalDateTime expireAt);

    void deleteAllByIdIn(Collection<Integer> ids);

    void deleteAllByCompareStringAndType(String compareString, String type);

    void deleteAllByExpireAtBefore(LocalDateTime expireAt);
}
