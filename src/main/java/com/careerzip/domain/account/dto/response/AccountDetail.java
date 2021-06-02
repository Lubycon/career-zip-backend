package com.careerzip.domain.account.dto.response;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.job.entity.Job;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Getter
public class AccountDetail {

    private final long id;

    @NotNull
    private final String name;

    @NotNull
    private final String email;

    @Nullable
    private final String avatarUrl;

    @Nullable
    @JsonIgnore
    private final String job;

    @Builder
    private AccountDetail(long id, String name, String email, String avatarUrl, String job) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.job = job;
    }

    public static AccountDetail from(Account account) {
        String jobName = Optional.ofNullable(account.getJob()).map(Job::getName).orElse(null);

        return AccountDetail.builder()
                            .id(account.getId())
                            .name(account.getName())
                            .email(account.getEmail())
                            .avatarUrl(account.getAvatarUrl())
                            .job(jobName)
                            .build();
    }
}
