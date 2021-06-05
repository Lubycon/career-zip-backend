package com.careerzip.domain.project.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.security.oauth.dto.OAuthAccount;

import java.util.List;
import java.util.Optional;

public interface ProjectRepositoryCustom {

    List<Project> findAllByAccount(Account account);

    List<Project> findAllByIds(List<Long> projectIds);

    Optional<Project> findBy(Account account, Long id);
}
