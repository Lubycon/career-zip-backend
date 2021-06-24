package com.careerzip.domain.archive.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface ArchiveRepositoryCustom {

    Page<Archive> findAllBy(Account account, Pageable pageable);

    Page<Archive> findAllBy(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Optional<Archive> findBy(Account account, Long archiveId);

    Optional<Archive> findBy(Account account, QuestionPaper questionPaper);
}
