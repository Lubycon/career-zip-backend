package com.careerzip.global.pagination;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.util.stream.Stream;

import static com.careerzip.testobject.pagination.PaginationFactory.createPaginationOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PaginationTest {

    @ParameterizedTest
    @MethodSource("validPaginationTestParams")
    @DisplayName("유효한 Pagination 파라미터가 들어왔을 때 해당 값이 세팅되는 테스트")
    void validPaginationTest(int page, int size, String direction) {
        // when
        Pagination pagination = createPaginationOf(page, size, direction);

        // then
        assertAll(
                () -> assertThat(pagination.getPage()).isEqualTo(page - 1),
                () -> assertThat(pagination.getSize()).isEqualTo(size),
                () -> assertThat(pagination.getDirection()).isEqualTo(Direction.valueOf(direction.toUpperCase()))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidPaginationTestParams")
    @DisplayName("유효하지 않은 Pagination 파라미터가 들어왔을 때 기본 값이 세팅되는 테스트")
    void invalidPaginationTest(int invalidPage, int invalidSize, String invalidDirection) {
        // given
        int defaultPage = 0;
        int defaultSize = 1;
        Direction defaultDirection = Direction.DESC;

        // when
        Pagination pagination = createPaginationOf(invalidPage, invalidSize, invalidDirection);

        // then
        assertAll(
                () -> assertThat(pagination.getPage()).isEqualTo(defaultPage),
                () -> assertThat(pagination.getSize()).isEqualTo(defaultSize),
                () -> assertThat(pagination.getDirection()).isEqualTo(defaultDirection)
        );
    }

    private static Stream<Arguments> validPaginationTestParams() {
        return Stream.of(
                Arguments.of(1, 1, "desc"), Arguments.of(1, 1, "asc"),
                Arguments.of(1, 1, "Desc"), Arguments.of(2, 2, "Asc"),
                Arguments.of(10, 1, "DESC"), Arguments.of(10, 3, "ASC")
        );
    }

    private static Stream<Arguments> invalidPaginationTestParams() {
        return Stream.of(
                Arguments.of(0, 0, "abc"), Arguments.of(0, 0, "1")
        );
    }
}
