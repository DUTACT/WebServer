package com.dutact.web.common.api;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponse<T> {
    private List<T> data;
    private PaginationMetadata pagination;

    public static <T> PageResponse<T> of(Page<T> page) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.data = page.getContent();
        
        PaginationMetadata paginationMetadata = new PaginationMetadata();
        paginationMetadata.totalData = (int) page.getTotalElements();
        paginationMetadata.totalPage = page.getTotalPages();
        paginationMetadata.currentPage = page.getNumber() + 1;
        paginationMetadata.pageSize = page.getSize();
        pageResponse.pagination = paginationMetadata;

        return pageResponse;
    }

    public static class PaginationMetadata {
        private Integer totalData;
        private Integer totalPage;
        private Integer currentPage;
        private Integer pageSize;
    }
}
