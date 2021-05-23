package com.careerzip.domain.archiving.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archiving.entity.Archiving;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArchivingRepository extends JpaRepository<Archiving, Long>, ArchivingRepositoryCustom {

    Page<Archiving> findAllBy(Account account, Pageable pageable);

    Optional<Archiving> findBy(Account account, Long recordId);
}
