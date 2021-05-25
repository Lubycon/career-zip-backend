package com.careerzip.domain.questiontype.repository;

import com.careerzip.domain.questiontype.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {
}
