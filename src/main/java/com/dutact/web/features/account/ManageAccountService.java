package com.dutact.web.features.account;

import com.dutact.web.auth.exception.UsernameOrEmailAlreadyExistException;
import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.account.dto.AccountDto;
import com.dutact.web.features.account.dto.CreateOrganizerAccountDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ManageAccountService {
    public PageResponse<AccountDto> getAllAccount(AccountQueryParams params);
    public AccountDto createOrganizer(CreateOrganizerAccountDto dto) throws UsernameOrEmailAlreadyExistException;
    void blockAccount(Integer accountId) throws NotExistsException;
    void unblockAccount(Integer accountId) throws NotExistsException;
}
