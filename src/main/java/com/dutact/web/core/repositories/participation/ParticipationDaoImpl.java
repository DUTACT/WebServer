package com.dutact.web.core.repositories.participation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipationDaoImpl implements ParticipationDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void confirmParticipation(ConfirmParticipationCondition condition) {

    }

    @Override
    public void rejectParticipation(RejectParticipationCondition condition) {

    }
}
