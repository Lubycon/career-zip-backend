package com.careerzip.domain.question.repository;

import com.careerzip.domain.questiontemplate.entity.QuestionTemplate;
import com.careerzip.domain.questiontemplate.repository.QuestionTemplateRepository;
import com.careerzip.domain.template.entity.Template;
import com.careerzip.domain.template.repository.TemplateRepository;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.careerzip.testobject.questiontemplate.QuestionTemplateFactory.createJpaQuestionTemplate;
import static com.careerzip.testobject.template.TemplateFactory.createJpaTestTemplate;
import static com.careerzip.testobject.question.QuestionFactory.createJpaTestQuestionOf;
import static org.assertj.core.api.Assertions.assertThat;

class QuestionRepositoryTest extends BaseRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    QuestionTemplateRepository questionTemplateRepository;

    @Test
    @DisplayName("Template 기준 리스트 조회 테스트")
    void findAllByTemplate() {
        // given
        List<QuestionTemplate> questionTemplates = Arrays.asList(createJpaQuestionTemplate(), createJpaQuestionTemplate(), createJpaQuestionTemplate());
        Template savedTemplate = templateRepository.save(createJpaTestTemplate());
        List<QuestionTemplate> savedQuestionTemplates = questionTemplateRepository.saveAll(questionTemplates);
        List<Question> questions =
                savedQuestionTemplates.stream()
                              .map(questionTemplate -> createJpaTestQuestionOf(savedTemplate, questionTemplate))
                              .collect(Collectors.toList());

        questionRepository.saveAll(questions);


        // when
        List<Question> foundQuestions = questionRepository.findAllBy(savedTemplate);

        // then
        assertThat(foundQuestions)
                .allMatch(templateQuestion -> templateQuestion.getTemplate().getId()
                                                                            .equals(savedTemplate.getId()));
    }
}
