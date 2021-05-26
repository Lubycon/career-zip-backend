package com.careerzip.domain.careerarchive.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.careerarchive.entity.CareerArchive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CareerArchiveRepositoryCustom {

    Page<CareerArchive> findAllBy(Account account, Pageable pageable);

    Optional<CareerArchive> findBy(Account account, Long recordId);
}
