package com.careerzip.domain.project.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.global.error.exception.entity.ProjectNotFoundException;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    @DisplayName("Id 리스트를 기준으로 Project 리스트를 조회하는 테스트")
    void findAllByIds() {
        // given
        Account account = accountRepository.save(createJpaMember());
        List<Project> projects = projectRepository.saveAll(Arrays.asList(createJpaProjectOf(account),
                                                                         createJpaProjectOf(account),
                                                                         createJpaProjectOf(account)));
        List<Long> projectIds = projects.stream()
                                        .map(Project::getId)
                                        .collect(Collectors.toList());

        // when
        List<Project> foundProjects = projectRepository.findAllByIds(projectIds);

        // then
        assertThat(foundProjects.size()).isEqualTo(projects.size());
    }

    @Test
    @DisplayName("Account, Id를 기준으로 Project를 조회하는 테스트")
    void findByAccountAndIdTest() {
        // given
        int first = 0;
        Account account = accountRepository.save(createJpaMember());
        List<Project> projects = projectRepository.saveAll(List.of(createJpaProjectOf(account), createJpaProjectOf(account),
                                                                   createJpaProjectOf(account)));
        Project project = projects.get(first);

        // when
        Project foundProject = projectRepository.findBy(account, project.getId()).orElseThrow(ProjectNotFoundException::new);

        // then
        assertThat(foundProject).usingRecursiveComparison().isEqualTo(project);
    }
}
