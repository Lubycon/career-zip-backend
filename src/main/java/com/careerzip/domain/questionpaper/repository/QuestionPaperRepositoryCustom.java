package com.careerzip.domain.questionpaper.repository;

import com.careerzip.domain.questionpaper.entity.QuestionPaper;

import java.util.Optional;

public interface QuestionPaperRepositoryCustom {

    Optional<QuestionPaper> findLatest();
}
