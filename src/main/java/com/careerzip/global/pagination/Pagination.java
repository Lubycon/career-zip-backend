package com.careerzip.global.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort.Direction;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Getter
@NoArgsConstructor
public class Pagination {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 30;
    private static final Direction DEFAULT_DIRECTION = ASC;

    private Integer page;
    private Integer size;
    private Direction direction;

    public Pagination(Integer page, Integer size, Direction direction) {
        this.page = page;
        this.size = size;
        this.direction = direction;
    }

    public static Pagination defaultPagination() {
        return new Pagination(DEFAULT_PAGE, DEFAULT_SIZE, DEFAULT_DIRECTION);
    }

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
