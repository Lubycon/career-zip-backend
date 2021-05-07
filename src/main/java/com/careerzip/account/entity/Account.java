package com.careerzip.account.entity;

import com.careerzip.global.jpa.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "oauth_id", nullable = false)
    private String oauthId;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private Provider provider;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "submit_count", nullable = false)
    private int submitCount;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @Builder
    public Account(String oauthId, Provider provider, String name, String email, String avatarUrl, Role role,
                   int submitCount, boolean deleted) {
        this.oauthId = oauthId;
        this.provider = provider;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.role = role;
        this.submitCount = submitCount;
        this.deleted = deleted;
    }

    @PrePersist
    public void prePersist() {
        // 새로운 계정 생성시 커리어 레터 제출 횟수에 대한 기본 값은 0 입니다.
        this.submitCount = 0;
        this.deleted = false;
    }
}

