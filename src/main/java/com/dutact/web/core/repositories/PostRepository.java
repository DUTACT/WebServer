package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.event.Event;
import com.dutact.web.core.entities.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    boolean existsByIdAndEventId(Integer postId, Integer eventId);

    Collection<Event> findAllByEventId(Integer eventId);

    @Query(value = "SELECT * FROM Post e WHERE e.status ->> 'type' = :status", nativeQuery = true)
    List<Event> findAllByStatus(String status);
}