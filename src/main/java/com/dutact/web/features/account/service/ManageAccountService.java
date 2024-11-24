package com.dutact.web.features.account.service;

import com.dutact.web.auth.exception.UsernameOrEmailAlreadyExistException;
import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.account.controller.AccountQueryParams;
import com.dutact.web.features.account.dto.AccountDto;
import com.dutact.web.features.account.dto.CreateOrganizerAccountDto;

public interface ManageAccountService {
    PageResponse<AccountDto> getAllAccount(AccountQueryParams params);

    AccountDto createOrganizer(CreateOrganizerAccountDto dto) throws UsernameOrEmailAlreadyExistException;

    void blockAccount(Integer accountId) throws NotExistsException;

    void unblockAccount(Integer accountId) throws NotExistsException;
}
