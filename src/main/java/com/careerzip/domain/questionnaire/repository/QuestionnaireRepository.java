package com.careerzip.domain.questionnaire.repository;

import com.careerzip.domain.questionnaire.entity.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {
}
