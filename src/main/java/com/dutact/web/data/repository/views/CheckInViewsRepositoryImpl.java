package com.dutact.web.data.repository.views;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.data.entity.common.UploadedFile;
import com.dutact.web.data.entity.eventregistration.participationcert.ParticipationCertificateStatus;
import com.dutact.web.data.projection.CheckInPreview;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CheckInViewsRepositoryImpl implements CheckInViewsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public PageResponse<CheckInPreview> getCheckInPreviews(CheckInQueryParams queryParams) {
        var query = """          
                WITH paged_registration AS (
                                         SELECT
                                             s.id AS student_id,
                                             s.full_name AS student_name,
                                             s.avatar AS student_avatar_url,
                                             er.certificate_status AS certificate_status
                                         FROM
                                             event_registration er
                                         JOIN
                                             student s
                                         ON
                                             er.student_id = s.id
                                         WHERE
                                             (:searchQuery = '' OR s.full_name LIKE :searchQuery)
                                             AND er.event_id = :eventId
                                         ORDER BY
                                             s.full_name
                                     ),
                                     check_in_counts AS (
                                         SELECT
                                             COUNT(ecc.id) AS total_check_ins,
                                             eci.student_id
                                         FROM
                                             event_check_in eci
                                         JOIN
                                             event_checkin_code ecc
                                         ON
                                             ecc.id = eci.check_in_code_id
                                         WHERE
                                             ecc.event_id = :eventId
                                             AND eci.student_id IN (SELECT student_id FROM paged_registration)
                                         GROUP BY
                                             eci.student_id
                                     )
                                     SELECT
                                         pr.student_id,
                                            pr.student_name,
                                            pr.student_avatar_url,
                                            pr.certificate_status,
                                         COALESCE(cic.total_check_ins, 0) AS total_check_ins
                                     FROM
                                         paged_registration pr
                                     LEFT JOIN
                                         check_in_counts cic
                                     ON
                                         pr.student_id = cic.student_id;
                """;

        var queryResultObject = (List<Object>) entityManager.createNativeQuery(query, Object.class)
                .setParameter("searchQuery", queryParams.getSearchQuery() == null ? "" : "%" + queryParams.getSearchQuery() + "%")
                .setParameter("eventId", queryParams.getEventId())
                .setFirstResult((queryParams.getPage() - 1) * queryParams.getPageSize())
                .setMaxResults(queryParams.getPageSize())
                .getResultList();

        var objectMapper = new ObjectMapper();

        var queryResult = queryResultObject.stream()
                .map(result -> {
                    try {
                        var resultArray = (Object[]) result;
                        var studentId = (Integer) resultArray[0];
                        var studentName = (String) resultArray[1];
                        var studentAvatar = objectMapper.readValue(resultArray[2].toString(), UploadedFile.class);
                        var certificateStatus = objectMapper.readValue(resultArray[3].toString(), ParticipationCertificateStatus.class);
                        var totalCheckIn = (Long) resultArray[4];
                        return new CheckInPreview(studentId, studentName, studentAvatar.getFileUrl(), totalCheckIn, certificateStatus);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class CheckInCountResult {
    private Long checkInCount;
    private Integer studentId;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class RegistrationResult {
    private Integer studentId;
    private String studentName;
    private UploadedFile studentAvatarUrl;
    private ParticipationCertificateStatus certificateStatus;
}