package com.careerzip.domain.record.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.record.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RecordRepositoryCustom {

    Page<Record> findAllBy(Account account, Pageable pageable);

    Optional<Record> findBy(Account account, Long recordId);
}
