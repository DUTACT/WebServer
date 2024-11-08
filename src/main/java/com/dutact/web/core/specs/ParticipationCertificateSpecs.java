package com.dutact.web.core.specs;

import com.dutact.web.core.entities.participationcert.ParticipationCertificate;
import org.springframework.data.jpa.domain.Specification;

public class ParticipationCertificateSpecs {
    private ParticipationCertificateSpecs() {
    }

    public static Specification<ParticipationCertificate> hasEventId(Integer eventId) {
        return (root, query, cb) -> cb.equal(root.get("event").get("id"), eventId);
    }
}
