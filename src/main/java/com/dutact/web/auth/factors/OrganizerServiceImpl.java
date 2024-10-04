package com.dutact.web.auth.factors;

import com.dutact.web.core.repositories.OrganizerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizerServiceImpl implements OrganizerService {

    private final OrganizerRepository organizerRepository;

    public OrganizerServiceImpl(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    @Override
    public Optional<Integer> getOrganizerId(String username) {
        return organizerRepository
                .findByUsername(username)
                .map(Account::getId);
    }
}
