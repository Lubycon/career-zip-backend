package com.careerzip.domain.templatequestion.repository;

import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.repository.QuestionRepository;
import com.careerzip.domain.template.entity.Template;
import com.careerzip.domain.template.repository.TemplateRepository;
import com.careerzip.domain.templatequestion.entity.TemplateQuestion;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.careerzip.testobject.question.QuestionFactory.createJpaTestQuestion;
import static com.careerzip.testobject.template.TemplateFactory.createJpaTestTemplate;
import static com.careerzip.testobject.templatequestion.TemplateQuestionFactory.createJpaTestTemplateQuestionOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TemplateQuestionRepositoryTest extends BaseRepositoryTest {

    @Autowired
    TemplateQuestionRepository templateQuestionRepository;

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Test
    @DisplayName("Template 기준 리스트 조회 테스트")
    void findAllByTemplate() {
        // given
        List<Question> questions = Arrays.asList(createJpaTestQuestion(), createJpaTestQuestion(), createJpaTestQuestion());
        Template savedTemplate = templateRepository.save(createJpaTestTemplate());
        List<Question> savedQuestions = questionRepository.saveAll(questions);
        List<TemplateQuestion> templateQuestions =
                savedQuestions.stream()
                              .map(question -> createJpaTestTemplateQuestionOf(savedTemplate, question))
                              .collect(Collectors.toList());

        templateQuestionRepository.saveAll(templateQuestions);


        // when
        List<TemplateQuestion> foundTemplateQuestions = templateQuestionRepository.findAllBy(savedTemplate);

        // then
        assertThat(foundTemplateQuestions)
                .allMatch(templateQuestion -> templateQuestion.getTemplate().getId()
                                                                            .equals(savedTemplate.getId()));
    }
}
