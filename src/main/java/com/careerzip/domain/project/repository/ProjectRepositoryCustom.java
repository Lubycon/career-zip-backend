package com.careerzip.domain.project.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.project.entity.Project;

import java.util.List;

public interface ProjectRepositoryCustom {

    List<Project> findAllByAccount(Account account);
}
