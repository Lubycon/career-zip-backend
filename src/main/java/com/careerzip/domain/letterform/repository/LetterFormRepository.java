package com.careerzip.domain.letterform.repository;

import com.careerzip.domain.letterform.entity.LetterForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterFormRepository extends JpaRepository<LetterForm, Long> {
}
