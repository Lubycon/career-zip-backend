package com.careerzip.domain.careerarchive.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.careerarchive.entity.CareerArchive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CareerArchiveRepository extends JpaRepository<CareerArchive, Long>, CareerArchiveRepositoryCustom {

    Page<CareerArchive> findAllBy(Account account, Pageable pageable);

    Optional<CareerArchive> findBy(Account account, Long recordId);
}
