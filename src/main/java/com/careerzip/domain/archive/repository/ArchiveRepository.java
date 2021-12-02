package com.careerzip.domain.archive.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ArchiveRepository extends JpaRepository<Archive, Long>, ArchiveRepositoryCustom {

    Page<Archive> findAllBy(Account account, Pageable pageable);

    Page<Archive> findAllBy(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Optional<Archive> findBy(Long archiveId);

    Optional<Archive> findBy(Account account, Long archiveId);

    Optional<Archive> findBy(Account account, QuestionPaper questionPaper);

    int deleteByIdIn(List<Long> ids);

    Page<Archive> findByAccount_Id(long accountId, Pageable pageable);
}
