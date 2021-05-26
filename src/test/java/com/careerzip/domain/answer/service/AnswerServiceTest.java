package com.careerzip.domain.answer.service;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.answer.repository.AnswerRepository;
import com.careerzip.domain.careerarchive.dto.response.archivingdetailresponse.QuestionWithAnswers;
import com.careerzip.domain.careerarchive.entity.CareerArchive;
import com.careerzip.domain.question.entity.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.careerzip.testobject.answer.AnswerFactory.createAnswers;
import static com.careerzip.testobject.careerarchive.CareerArchiveFactory.createCareerArchive;
import static com.careerzip.testobject.question.QuestionFactory.createQuestions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {

    @InjectMocks
    AnswerService answerService;

    @Mock
    AnswerRepository answerRepository;

    @Test
    @DisplayName("Questions에 대한 Answers를 그룹핑 한 뒤 반환하는 테스트")
    void groupingAnswersByTest() {
        // given
        CareerArchive careerArchive = createCareerArchive();
        List<Question> questions = createQuestions();
        List<Answer> testAnswers = createAnswers();

        // when
        when(answerRepository.findAllBy(any(CareerArchive.class), anyList())).thenReturn(testAnswers);

        List<QuestionWithAnswers> questionWithAnswers = answerService.groupingAnswersBy(careerArchive, questions);

        // then
        assertThat(questionWithAnswers.size()).isEqualTo(questions.size());
    }
}
