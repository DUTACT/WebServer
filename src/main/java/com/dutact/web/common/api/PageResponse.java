package com.dutact.web.common.api;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Getter
public class PageResponse<T> {
    private List<T> data;
    private PaginationMetadata pagination;

    public static <T> PageResponse<T> of(
            List<T> data,
            int totalData,
            int currentPage,
            int pageSize) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.data = data;
        pageResponse.pagination = new PaginationMetadata();
        pageResponse.pagination.totalData = totalData;
        pageResponse.pagination.totalPage = (int) Math.ceil((double) totalData / pageSize);
        pageResponse.pagination.currentPage = currentPage;
        pageResponse.pagination.pageSize = pageSize;

        return pageResponse;
    }

    public static <S, T> PageResponse<T> of(PageResponse<S> pageResponse, Function<S, T> mapper) {
        PageResponse<T> newPageResponse = new PageResponse<>();
        newPageResponse.data = pageResponse.getData().stream().map(mapper).toList();
        newPageResponse.pagination = pageResponse.getPagination();

        return newPageResponse;
    }

    private static PaginationMetadata createPaginationMetadata(Page<?> page) {
        PaginationMetadata paginationMetadata = new PaginationMetadata();
        paginationMetadata.totalData = (int) page.getTotalElements();
        paginationMetadata.totalPage = page.getTotalPages();
        paginationMetadata.currentPage = page.getNumber();
        paginationMetadata.pageSize = page.getSize();
        return paginationMetadata;
    }

    @Getter
    public static class PaginationMetadata {
        private Integer totalData;
        private Integer totalPage;
        private Integer currentPage;
        private Integer pageSize;
    }
}
