package com.careerzip.domain.account.entity;

import com.careerzip.domain.account.dto.request.AccountRequest;
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
    private String oAuthId;

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
    private Account(Long id, String oAuthId, Provider provider, String name, String email, String avatarUrl, Role role,
                   int submitCount, boolean deleted) {
        // NOTICE: 오직 ID 값이 필요한 테스트 객체를 위한 코드이며, 테스트가 아닌 환경에서 모든 객체는 빌더가 아닌 정적 팩토리 메서드를 통해서만 생성해야 합니다.
        this.id = id;
        this.oAuthId = oAuthId;
        this.provider = provider;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.role = role;
        this.submitCount = submitCount;
        this.deleted = deleted;
    }

    public String getRoleValue() {
        return role.getValue();
    }
}

