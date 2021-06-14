package com.careerzip.domain.archive.repository;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArchiveRepository extends JpaRepository<Archive, Long>, ArchiveRepositoryCustom {

    Page<Archive> findAllBy(Account account, Pageable pageable);

    Optional<Archive> findBy(Account account, Long archiveId);

    Optional<Archive> findBy(Account account, QuestionPaper questionPaper);
}
