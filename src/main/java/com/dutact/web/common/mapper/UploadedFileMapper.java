package com.dutact.web.common.mapper;

import com.dutact.web.core.entities.common.UploadedFile;
import com.dutact.web.storage.UploadFileResult;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UploadedFileMapper {
    UploadedFile toUploadedFile(UploadFileResult uploadFileResult);
}
