package com.dutact.web.core.repositories;

import com.dutact.web.core.entities.participationcert.ParticipationCertificate;
import com.dutact.web.core.entities.participationcert.ParticipationCertificateId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationCertificateRepository
        extends JpaRepository<ParticipationCertificate, ParticipationCertificateId> {
}
