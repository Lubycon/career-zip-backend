package com.careerzip.domain.template.repository;

import com.careerzip.domain.template.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {
}
