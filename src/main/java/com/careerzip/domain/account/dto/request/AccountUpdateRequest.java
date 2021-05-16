package com.careerzip.domain.account.dto.request;


import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class AccountUpdateRequest {

    @NotEmpty
    @Size(min = 1, max = 16, message = "닉네임은 1자 ~ 16자 사이 입니다.")
    private String name;

    @NotNull
    @Email(message = "유효한 이메일 주소가 아닙니다. 다시 입력 해주세요.")
    private String email;

    AccountUpdateRequest( String name,  String email) {
        this.name = name;
        this.email = email;
    }
}
