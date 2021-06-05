package com.careerzip.domain.project.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.security.oauth.dto.OAuthAccount;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.careerzip.domain.project.entity.QProject.project;

@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Project> findAllByAccount(Account account) {
        return queryFactory.selectFrom(project)
                           .where(project.account.eq(account))
                           .fetch();
    }

    public List<Project> findAllByIds(List<Long> projectIds) {
        return queryFactory.selectFrom(project)
                           .where(project.id.in(projectIds))
                           .fetch();
    }

    public Optional<Project> findBy(Account account, Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(project)
                                               .where(project.account.eq(account), project.id.eq(id))
                                               .fetchOne());
    }
}
