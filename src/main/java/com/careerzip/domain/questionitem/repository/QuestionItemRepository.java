package com.careerzip.domain.questionitem.repository;

import com.careerzip.domain.questionitem.entity.QuestionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionItemRepository extends JpaRepository<QuestionItem, Long> {
}
