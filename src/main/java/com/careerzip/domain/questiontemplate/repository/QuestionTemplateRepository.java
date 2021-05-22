package com.careerzip.domain.questiontemplate.repository;

import com.careerzip.domain.questiontemplate.entity.QuestionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTemplateRepository extends JpaRepository<QuestionTemplate, Long> {
}
