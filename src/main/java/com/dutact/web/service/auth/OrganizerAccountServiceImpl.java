package com.dutact.web.service.auth;

import com.dutact.web.data.entity.auth.Account;
import com.dutact.web.data.repository.OrganizerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizerAccountServiceImpl implements OrganizerAccountService {

    private final OrganizerRepository organizerRepository;

    public OrganizerAccountServiceImpl(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    @Override
    public Optional<Integer> getOrganizerId(String username) {
        return organizerRepository
                .findByUsername(username)
                .map(Account::getId);
    }
}
