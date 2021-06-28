package com.careerzip.domain.questionpaper.repository;

import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionPaperRepository extends JpaRepository<QuestionPaper, Long>, QuestionPaperRepositoryCustom {

    Optional<QuestionPaper> findLatest();

    List<QuestionPaper> findAllByOrderByIdDesc();
}
