package com.careerzip.domain.project.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.domain.project.entity.QProject;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.careerzip.domain.project.entity.QProject.project;

@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Project> findAllByAccount(Account account) {
        return queryFactory.selectFrom(project)
                           .where(project.account.eq(account))
                           .fetch();
    }
}
