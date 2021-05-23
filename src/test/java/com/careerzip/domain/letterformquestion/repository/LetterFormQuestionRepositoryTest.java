package com.careerzip.domain.letterformquestion.repository;

import com.careerzip.domain.letterform.entity.LetterForm;
import com.careerzip.domain.letterformquestion.entity.LetterFormQuestion;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.repository.QuestionRepository;
import com.careerzip.domain.letterform.repository.LetterFormRepository;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.careerzip.testobject.question.QuestionFactory.createJpaQuestionTemplate;
import static com.careerzip.testobject.letterform.TemplateFactory.createJpaTestLetterFormOf;
import static com.careerzip.testobject.letterformquestion.LetterFormQuestionFactory.createJpaTestLetterFormQuestionOf;
import static org.assertj.core.api.Assertions.assertThat;

class LetterFormQuestionRepositoryTest extends BaseRepositoryTest {

    @Autowired
    LetterFormQuestionRepository letterFormQuestionRepository;

    @Autowired
    LetterFormRepository letterFormRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Test
    @DisplayName("Template 기준 리스트 조회 테스트")
    void findAllByTemplate() {
        // given
        List<Question> questions = Arrays.asList(createJpaQuestionTemplate(), createJpaQuestionTemplate(), createJpaQuestionTemplate());
        LetterForm savedLetterForm = letterFormRepository.save(createJpaTestLetterFormOf());
        List<Question> savedQuestions = questionRepository.saveAll(questions);
        List<LetterFormQuestion> letterFormQuestions =
                savedQuestions.stream()
                              .map(questionTemplate -> createJpaTestLetterFormQuestionOf(savedLetterForm, questionTemplate))
                              .collect(Collectors.toList());

        letterFormQuestionRepository.saveAll(letterFormQuestions);


        // when
        List<LetterFormQuestion> foundLetterFormQuestions = letterFormQuestionRepository.findAllBy(savedLetterForm);

        // then
        assertThat(foundLetterFormQuestions)
                .allMatch(templateQuestion -> templateQuestion.getLetterForm().getId()
                                                                            .equals(savedLetterForm.getId()));
    }
}
