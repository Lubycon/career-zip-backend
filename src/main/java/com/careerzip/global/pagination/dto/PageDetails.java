package com.careerzip.global.pagination.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageDetails {

    private final long totalPages;
    private final int currentPage;
    private final long totalDataCount;
    private final int currentPageSize;
    private final boolean firstPage;
    private final boolean lastPage;

    @Builder
    private PageDetails(long totalPages, int currentPage, long totalDataCount, int currentPageSize,
                        boolean firstPage, boolean lastPage) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.totalDataCount = totalDataCount;
        this.currentPageSize = currentPageSize;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
    }

    public static <T> PageDetails from(Page<T> page) {
        return PageDetails.builder()
                          .totalPages(page.getTotalPages())
                          .currentPage(page.getNumber() + 1)
                          .totalDataCount(page.getTotalElements())
                          .currentPageSize(page.getNumberOfElements())
                          .firstPage(page.isFirst())
                          .lastPage(page.isLast())
                          .build();
    }
}
