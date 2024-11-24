package com.dutact.web.features.account.service;

import com.dutact.web.auth.exception.UsernameOrEmailAlreadyExistException;
import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.features.account.dto.AccountDto;
import com.dutact.web.features.account.dto.CreateOrganizerAccountDto;
import com.dutact.web.features.account.dto.OrganizerAccountDto;
import com.dutact.web.features.account.dto.StudentAccountDto;

public interface ManageAccountService {
    PageResponse<AccountDto> getAllAccount(AccountQueryParams params);

    PageResponse<StudentAccountDto> getAllStudentAccount(RoleSpecifiedAccountQueryParams params);

    PageResponse<OrganizerAccountDto> getAllOrganizerAccount(RoleSpecifiedAccountQueryParams params);

    OrganizerAccountDto createOrganizer(CreateOrganizerAccountDto dto) throws UsernameOrEmailAlreadyExistException;

    void blockAccount(Integer accountId) throws NotExistsException;

    void unblockAccount(Integer accountId) throws NotExistsException;
}
