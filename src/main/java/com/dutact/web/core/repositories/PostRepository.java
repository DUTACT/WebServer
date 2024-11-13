package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.post.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    boolean existsByIdAndEventId(Integer postId, Integer eventId);
    List<Post> findAllByEventId(Integer eventId, Sort sort);
    List<Post> findAllByEventId(Integer eventId);
    List<Post> findAllByEventIdIn(List<Integer> eventIds);

    @Query(value = "SELECT * FROM Post e WHERE e.status ->> 'type' = :status", nativeQuery = true)
    List<Post> findAllByStatus(String status);

    @Query(value = "SELECT * FROM Post e WHERE e.event_id = :eventId AND e.status ->> 'type' = :status", nativeQuery = true)
    List<Post> findAllByEventIdAndStatus(Integer eventId, String status);

    Optional<Post> findByIdAndEventId(Integer postId, Integer eventId);
}