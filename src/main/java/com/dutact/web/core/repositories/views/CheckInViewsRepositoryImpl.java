package com.dutact.web.core.repositories.views;

import com.dutact.web.core.projections.CheckInPreview;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public class CheckInViewsRepositoryImpl implements CheckInViewsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<CheckInPreview> getCheckInPreviews(CheckInQueryParams queryParams) {
        String query = """
                SELECT check_in_preview.studentId, check_in_preview.studentName, check_in_preview.totalCheckIn
                FROM (
                    SELECT student.id as studentId, student.fullName as studentName, COUNT(student.id) as totalCheckIn
                    FROM EventCheckInCode check_in_code
                        JOIN EventCheckIn event_check_in ON check_in_code.id = event_check_in.checkInCode.id
                        JOIN Student student ON event_check_in.student.id = student.id
                    WHERE student.fullName LIKE %:searchQuery%
                        AND check_in_code.event.id = :eventId
                    GROUP BY student.id, student.fullName
                ) AS check_in_preview
                WHERE check_in_preview.totalCheckIn > 0
                LIMIT :from, :to;
                """;


        String countQuery = "SELECT COUNT(DISTINCT student_id) " +
                "FROM event_check_in " +
                "WHERE check_in_code_id IN (SELECT id FROM event_checkin_code WHERE event_id = :eventId) " +
                "GROUP BY student_id, student_name " +
                "HAVING student_name LIKE :searchQuery";

        var from = queryParams.getPage() * queryParams.getPageSize();
        var to = from + queryParams.getPageSize();

        var results = entityManager.createQuery(query, "CheckInPreview")
                .setParameter("eventId", queryParams.getEventId())
                .setParameter("searchQuery", "%" + queryParams.getSearchQuery() + "%")
                .setFirstResult(from)
                .setMaxResults(to)
                .getResultList();

        var count = entityManager.createNativeQuery(countQuery)
                .setParameter("eventId", queryParams.getEventId())
                .setParameter("searchQuery", "%" + queryParams.getSearchQuery() + "%")
                .getResultList().size();

        return new PageImpl<>(results,
                PageRequest.of(queryParams.getPage(), queryParams.getPageSize()),
                count);
/*        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

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
                count);*/
    }
}
