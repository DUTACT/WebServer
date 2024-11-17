package com.dutact.web.features.account;

import com.dutact.web.auth.exception.UsernameOrEmailAlreadyExistException;
import com.dutact.web.auth.factors.Account;
import com.dutact.web.auth.factors.AccountRepository;
import com.dutact.web.auth.factors.Role;
import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.core.entities.EventOrganizer;
import com.dutact.web.core.repositories.OrganizerRepository;
import com.dutact.web.features.account.dto.AccountDto;
import com.dutact.web.features.account.dto.CreateOrganizerAccountDto;
import com.dutact.web.features.account.service.AccountMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManageAccountServiceImpl implements ManageAccountService{
    private AccountRepository accountRepository;
    private OrganizerRepository organizerRepository;
    private AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public PageResponse<AccountDto> getAllAccount(AccountQueryParams params) {
        Pageable pageable = PageRequest.of(params.getPage() - 1, params.getPageSize(), Sort.by(Sort.Direction.ASC, "username"));

        List<Account> accounts = accountRepository.findAll(Sort.by(Sort.Direction.ASC, "username"));
        List<Account> filteredAccount = accounts.stream().filter(
                a -> {
                    if (params.getRole() != null && !a.getRole().equals(params.getRole())){
                        return false;
                    }
                    if (params.getSearchQuery() != null && !a.getUsername().contains(params.getSearchQuery())){
                        return false;
                    }
                    return true;
                }
        ).toList();

        Page<Account> accountsPage = new PageImpl<>(
                slice(filteredAccount, pageable.getPageNumber() * pageable.getPageSize(), (pageable.getPageNumber() + 1) * pageable.getPageSize()),
                pageable,
                filteredAccount.size()
        );
        return PageResponse.of(
                accountsPage,
                accountMapper::toAccountDto
        );
    }

    @Override
    public AccountDto createOrganizer(CreateOrganizerAccountDto dto) throws UsernameOrEmailAlreadyExistException {
        Optional<Account> account = accountRepository.findByUsername(dto.getUsername());
        if (account.isPresent()){
            throw new UsernameOrEmailAlreadyExistException();
        }

        EventOrganizer organizer = accountMapper.toOrganizer(dto);
        organizer.setEnabled(true);
        organizer.setPassword(passwordEncoder.encode(dto.getPassword()));
        organizer.setRole(Role.EVENT_ORGANIZER);
        organizer.setName(organizer.getUsername());
        return accountMapper.toAccountDto(organizerRepository.save(organizer));
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

    public static <T> List<T> slice(List<T> list, int begin, int end) {
        if (begin < 0) begin = 0;
        if (end > list.size()) end = list.size();
        if (begin > end) begin = end;
        
        return list.subList(begin, end);
    }

    public static <T> List<T> slice(List<T> list, int begin) {
        return slice(list, begin, list.size());
    }
}
