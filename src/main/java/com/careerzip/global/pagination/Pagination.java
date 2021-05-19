package com.careerzip.global.pagination;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort.Direction;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Getter
public class Pagination {

    private Integer page;
    private Integer size;
    private Direction direction;

    public void setPage(Integer page) {
        if (page == null || page < 1) {
            this.page = 0;
            return;
        }
        this.page = page - 1;
    }

    public void setSize(Integer size) {
        if (size == null || size < 1) {
            this.size = 1;
            return;
        }
        this.size = size;
    }

    public void setDirection(String direction) {
        if (StringUtils.equalsAnyIgnoreCase(direction, DESC.name(), ASC.name())) {
            this.direction = Direction.valueOf(direction.toUpperCase());
            return;
        }
        this.direction = DESC;
    }
}
