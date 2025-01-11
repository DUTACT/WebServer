package com.dutact.web.service.account;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.data.entity.EventOrganizer;
import com.dutact.web.data.entity.auth.Account;
import com.dutact.web.data.entity.auth.Role;
import com.dutact.web.data.repository.OrganizerRepository;
import com.dutact.web.data.repository.StudentRepository;
import com.dutact.web.data.repository.auth.AccountRepository;
import com.dutact.web.dto.account.AccountDto;
import com.dutact.web.dto.account.CreateOrganizerAccountDto;
import com.dutact.web.dto.account.OrganizerAccountDto;
import com.dutact.web.dto.account.StudentAccountDto;
import com.dutact.web.service.auth.exception.UsernameOrEmailAlreadyExistException;
import com.dutact.web.specs.AccountSpecs;
import com.dutact.web.specs.OrganizerSpecs;
import com.dutact.web.specs.StudentSpecs;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ManageAccountServiceImpl implements ManageAccountService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final StudentRepository studentRepository;
    private final OrganizerRepository organizerRepository;
    private final AccountMapper accountMapper;

    @Override
    public PageResponse<AccountDto> getAllAccount(AccountQueryParams params) {
        var pageable = PageRequest.of(params.getPage() - 1,
                params.getPageSize(),
                Sort.by(Sort.Direction.ASC, "username"));

        var spec = AccountSpecs.emtpy();
        if (params.getRole() != null) {
            spec = spec.and(AccountSpecs.hasRole(params.getRole()));
        }

        if (params.getSearchQuery() != null) {
            spec = spec.and(AccountSpecs.usernameContains(params.getSearchQuery()));
        }

        var accountsPage = accountRepository.findAll(spec, pageable);

        return PageResponse.of(accountsPage, accountMapper::toDto);
    }

    @Override
    public PageResponse<StudentAccountDto> getAllStudentAccount(RoleSpecifiedAccountQueryParams params) {
        var pageable = PageRequest.of(params.getPage() - 1,
                params.getPageSize(),
                Sort.by(Sort.Direction.ASC, "username"));

        var spec = StudentSpecs.emtpy();

        if (params.getSearchQuery() != null) {
            spec = spec.and(StudentSpecs.usernameContains(params.getSearchQuery()));
        }

        var accountsPage = studentRepository.findAll(spec, pageable);

        return PageResponse.of(accountsPage, accountMapper::toDto);
    }

    @Override
    public PageResponse<OrganizerAccountDto> getAllOrganizerAccount(RoleSpecifiedAccountQueryParams params) {
        var pageable = PageRequest.of(params.getPage() - 1,
                params.getPageSize(),
                Sort.by(Sort.Direction.ASC, "username"));

        var spec = OrganizerSpecs.emtpy();

        if (params.getSearchQuery() != null) {
            spec = spec.and(OrganizerSpecs.usernameContains(params.getSearchQuery()));
        }

        var accountsPage = organizerRepository.findAll(spec, pageable);

        return PageResponse.of(accountsPage, accountMapper::toDto);
    }

    @Override
    public OrganizerAccountDto createOrganizer(CreateOrganizerAccountDto dto) throws UsernameOrEmailAlreadyExistException {
        Optional<Account> account = accountRepository.findByUsername(dto.getUsername());
        if (account.isPresent()) {
            throw new UsernameOrEmailAlreadyExistException();
        }

        EventOrganizer organizer = accountMapper.toOrganizer(dto);
        organizer.setEnabled(true);
        organizer.setPassword(passwordEncoder.encode(dto.getPassword()));
        organizer.setRole(Role.EVENT_ORGANIZER);
        return accountMapper.toDto(organizerRepository.save(organizer));
    }

    @Override
    public void blockAccount(Integer accountId) throws NotExistsException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotExistsException("Account not found"));
        account.setEnabled(false);
        accountRepository.save(account);
    }

    @Override
    public void unblockAccount(Integer accountId) throws NotExistsException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotExistsException("Account not found"));
        account.setEnabled(true);
        accountRepository.save(account);
    }
}
