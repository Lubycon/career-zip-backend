package com.careerzip.domain.question.repository;

import com.careerzip.domain.letterform.entity.LetterForm;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.questionitem.entity.QuestionItem;
import com.careerzip.domain.letterform.repository.LetterFormRepository;
import com.careerzip.domain.questionitem.repository.QuestionItemRepository;
import com.careerzip.testconfig.base.BaseRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.careerzip.testobject.questionitem.QuestionItemFactory.createJpaTestTextQuestionItem;
import static com.careerzip.testobject.letterform.LetterFormFactory.createJpaTestLetterForm;
import static com.careerzip.testobject.question.QuestionFactory.createJpaTestQuestionOf;
import static org.assertj.core.api.Assertions.assertThat;

class QuestionRepositoryTest extends BaseRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    LetterFormRepository letterFormRepository;

    @Autowired
    QuestionItemRepository questionItemRepository;

    @Test
    @DisplayName("LetterForm 기준 리스트 조회 테스트")
    void findAllByLetterForm() {
        // given
        List<QuestionItem> questionItems = Arrays.asList(createJpaTestTextQuestionItem(), createJpaTestTextQuestionItem(), createJpaTestTextQuestionItem());
        LetterForm savedLetterForm = letterFormRepository.save(createJpaTestLetterForm());
        List<QuestionItem> savedQuestionItems = questionItemRepository.saveAll(questionItems);
        List<Question> questions =
                savedQuestionItems.stream()
                              .map(letterFormQuestion -> createJpaTestQuestionOf(savedLetterForm, letterFormQuestion))
                              .collect(Collectors.toList());

        questionRepository.saveAll(questions);


        // when
        List<Question> foundQuestions = questionRepository.findAllBy(savedLetterForm);

        // then
        assertThat(foundQuestions)
                .allMatch(letterFormQuestion -> letterFormQuestion.getLetterForm().getId()
                                                                                  .equals(savedLetterForm.getId()));
    }
}
