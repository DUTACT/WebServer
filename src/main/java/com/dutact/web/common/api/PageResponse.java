package com.dutact.web.common.api;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Getter
public class PageResponse<T> {
    private List<T> data;
    private PaginationMetadata pagination;

    public static <T> PageResponse<T> of(Page<T> page) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.data = page.getContent();
        pageResponse.pagination = createPaginationMetadata(page);

        return pageResponse;
    }

    public static <S, T> PageResponse<T> of(Page<S> page, Function<S, T> mapper) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.data = page.getContent().stream().map(mapper).toList();
        pageResponse.pagination = createPaginationMetadata(page);

        return pageResponse;
    }

    private static PaginationMetadata createPaginationMetadata(Page<?> page) {
        PaginationMetadata paginationMetadata = new PaginationMetadata();
        paginationMetadata.totalData = (int) page.getTotalElements();
        paginationMetadata.totalPage = page.getTotalPages();
        paginationMetadata.currentPage = page.getNumber() + 1;
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
