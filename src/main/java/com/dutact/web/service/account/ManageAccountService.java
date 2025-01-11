package com.dutact.web.service.account;

import com.dutact.web.common.api.PageResponse;
import com.dutact.web.common.api.exceptions.NotExistsException;
import com.dutact.web.dto.account.AccountDto;
import com.dutact.web.dto.account.CreateOrganizerAccountDto;
import com.dutact.web.dto.account.OrganizerAccountDto;
import com.dutact.web.dto.account.StudentAccountDto;
import com.dutact.web.service.auth.exception.UsernameOrEmailAlreadyExistException;

public interface ManageAccountService {
    PageResponse<AccountDto> getAllAccount(AccountQueryParams params);

    PageResponse<StudentAccountDto> getAllStudentAccount(RoleSpecifiedAccountQueryParams params);

    PageResponse<OrganizerAccountDto> getAllOrganizerAccount(RoleSpecifiedAccountQueryParams params);

    OrganizerAccountDto createOrganizer(CreateOrganizerAccountDto dto) throws UsernameOrEmailAlreadyExistException;

    void blockAccount(Integer accountId) throws NotExistsException;

    void unblockAccount(Integer accountId) throws NotExistsException;
}
