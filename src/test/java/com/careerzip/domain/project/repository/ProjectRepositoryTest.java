package com.careerzip.domain.project.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static com.careerzip.testobject.account.AccountFactory.createJpaMember;
import static com.careerzip.testobject.project.ProjectFactory.createJpaProjectOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProjectRepositoryTest extends BaseRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("Account를 기준으로 Project 리스트를 조회하는 테스트")
    void findAllByAccountTest() {
        // given
        Account account = accountRepository.save(createJpaMember());
        List<Project> savedProjects = projectRepository.saveAll(Arrays.asList(createJpaProjectOf(account), createJpaProjectOf(account),
                                                                              createJpaProjectOf(account)));

        // when
        List<Project> projects = projectRepository.findAllByAccount(account);

        // then
        assertThat(projects).usingRecursiveComparison().isEqualTo(savedProjects);
    }
}
