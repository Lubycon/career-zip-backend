package com.careerzip.domain.project.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {

    List<Project> findAllByAccount(Account account);

    List<Project> findAllByIds(List<Long> projectIds);
}
