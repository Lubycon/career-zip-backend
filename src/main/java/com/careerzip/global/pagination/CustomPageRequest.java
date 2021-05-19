package com.careerzip.global.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class CustomPageRequest {

    private static final String CREATED_DATE_TIME = "createdDateTime";

    public static PageRequest of(Pagination pagination) {
        Sort sort = Sort.by(pagination.getDirection(), CREATED_DATE_TIME);
        return PageRequest.of(pagination.getPage(), pagination.getSize(), sort);
    }
}
