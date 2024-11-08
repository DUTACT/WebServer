package com.dutact.web.core.repositories.views;

import com.dutact.web.core.entities.EventCheckIn;
import com.dutact.web.core.projections.CheckInPreview;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.function.BiFunction;

@Repository
public class CheckInViewsRepositoryImpl implements CheckInViewsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<CheckInPreview> getCheckInPreviews(CheckInQueryParams queryParams) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<CheckInPreview> query = cb.createQuery(CheckInPreview.class);

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Subquery<Long> countSubQuery = countQuery.subquery(Long.class);

        Root<EventCheckIn> root = query.from(EventCheckIn.class);
        Root<EventCheckIn> countSubqueryRoot = countSubQuery.from(EventCheckIn.class);

        query.multiselect(
                root.get("student").get("id").alias("studentId"),
                root.get("student").get("fullName").alias("studentName"),
                cb.count(root).alias("totalCheckIn")
        );
        countSubQuery.select(cb.countDistinct(countSubqueryRoot.get("student").get("id"))).alias("count");

        BiFunction<Root<?>, AbstractQuery<?>, AbstractQuery<?>> where = (queryRoot, criteriaQuery) -> {
            criteriaQuery.where(cb.equal(queryRoot
                    .get("checkInCode").get("event").get("id"), queryParams.getEventId()));
            if (queryParams.getSearchQuery() != null) {
                criteriaQuery.having(cb.like(queryRoot
                        .get("student").get("fullName"), "%" + queryParams.getSearchQuery() + "%"));
            }
            return criteriaQuery;
        };

        where.apply(root, query);
        where.apply(countSubqueryRoot, countSubQuery);

        BiFunction<Root<?>, AbstractQuery<?>, AbstractQuery<?>> groupBy =
                (queryRoot, criteriaQuery) ->
                        criteriaQuery
                                .groupBy(queryRoot.get("student").get("id"),
                                        queryRoot.get("student").get("fullName"));


        groupBy.apply(root, query);
        groupBy.apply(countSubqueryRoot, countSubQuery);

        var from = queryParams.getPage() * queryParams.getPageSize();
        var to = from + queryParams.getPageSize();

        var results = entityManager.createQuery(query)
                .setFirstResult(from)
                .setMaxResults(to)
                .getResultList();

        var countRoot = countQuery.from(countSubQuery.getResultType());
        countQuery.select(countRoot.get("count"))
                .where(cb.equal(countRoot.get("count"), countSubQuery.getCorrelatedRoots().get(0).get("count")));

        var count = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(results,
                PageRequest.of(queryParams.getPage(), queryParams.getPageSize()),
                count);
    }
}
