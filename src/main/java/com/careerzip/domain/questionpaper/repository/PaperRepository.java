package com.careerzip.domain.questionpaper.repository;

import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepository extends JpaRepository<QuestionPaper, Long> {
}
