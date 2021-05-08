package com.careerzip.global.api;

import lombok.Getter;

@Getter
public class ApiResult<T> {

    private final T data;

    private ApiResult(T data) {
        this.data = data;
    }

    public static <T> ApiResult<T> SUCCESS(T data) {
        return new ApiResult<>(data);
    }
}
