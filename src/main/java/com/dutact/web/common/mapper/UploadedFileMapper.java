package com.dutact.web.common.mapper;

import com.dutact.web.common.storage.UploadFileResult;
import com.dutact.web.data.entity.common.UploadedFile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UploadedFileMapper {
    UploadedFile toUploadedFile(UploadFileResult uploadFileResult);
}
