package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.feedback.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer>, JpaSpecificationExecutor<Feedback> {
}
