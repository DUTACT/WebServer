package com.dutact.web.core.repositories.views;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.core.entities.eventregistration.participationcert.ParticipationCertificateStatus;
import com.dutact.web.core.projections.CheckInPreview;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CheckInViewsRepositoryImpl implements CheckInViewsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PageResponse<CheckInPreview> getCheckInPreviews(CheckInQueryParams queryParams) {
        var query = """
                SELECT check_in_preview.studentId, check_in_preview.studentName,
                       check_in_preview.totalCheckIn, check_in_preview.certificateStatus
                FROM (
                    SELECT student.id as studentId, student.fullName as studentName,
                            registration.certificateStatus as certificateStatus,
                            COUNT(event_check_in.id) as totalCheckIn
                    FROM EventRegistration registration
                        JOIN Student student ON registration.student.id = student.id
                        JOIN Event event ON registration.event.id = event.id
                        LEFT JOIN EventCheckIn event_check_in ON student.id = event_check_in.student.id
                    WHERE (:searchQuery IS NULL OR student.fullName LIKE :searchQuery)
                        AND event.id = :eventId
                    GROUP BY student.id, student.fullName, registration.certificateStatus
                    ORDER BY student.fullName
                ) AS check_in_preview
                """;

        var objQueryResult = entityManager.createQuery(query, Object.class)
                .setParameter("searchQuery", queryParams.getSearchQuery() == null ? null : "%" + queryParams.getSearchQuery() + "%")
                .setParameter("eventId", queryParams.getEventId())
                .setFirstResult((queryParams.getPage() - 1) * queryParams.getPageSize())
                .setMaxResults(queryParams.getPageSize())
                .getResultList();

        var queryResult = objQueryResult.stream()
                .map(obj -> {
                    var arr = (Object[]) obj;
                    return new CheckInPreview(
                            (Integer) arr[0],
                            (String) arr[1],
                            (Long) arr[2],
                            (ParticipationCertificateStatus) arr[3]
                    );
                })
                .toList();

        var countQuery = """
                SELECT COUNT(DISTINCT student.id)
                FROM EventRegistration registration
                WHERE (:searchQuery IS NULL OR student.fullName LIKE :searchQuery)
                    AND event.id = :eventId
                """;

        var count = entityManager.createQuery(countQuery, Long.class)
                .setParameter("searchQuery", queryParams.getSearchQuery() == null ? null : "%" + queryParams.getSearchQuery() + "%")
                .setParameter("eventId", queryParams.getEventId())
                .getSingleResult();

        return PageResponse.of(queryResult,
                count.intValue(),
                queryParams.getPage(),
                queryParams.getPageSize());
    }
}
