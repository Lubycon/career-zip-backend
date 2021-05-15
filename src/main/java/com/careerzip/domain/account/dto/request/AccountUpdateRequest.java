package com.careerzip.domain.account.dto.request;


import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public class AccountUpdateRequest {

    @Nullable
    private String name;

    @Nullable
    private String email;
}
