package com.careerzip.testobject.pagination;

import com.careerzip.global.pagination.Pagination;

public class PaginationFactory {

    // Pagination
    public static Pagination createPaginationOf(int page, int size, String direction) {
        Pagination pagination = new Pagination();
        pagination.setPage(page);
        pagination.setSize(size);
        pagination.setDirection(direction);
        return pagination;
    }
}
