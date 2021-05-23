package com.careerzip.domain.archiving.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archiving.entity.Archiving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ArchivingRepositoryCustom {

    Page<Archiving> findAllBy(Account account, Pageable pageable);

    Optional<Archiving> findBy(Account account, Long recordId);
}
