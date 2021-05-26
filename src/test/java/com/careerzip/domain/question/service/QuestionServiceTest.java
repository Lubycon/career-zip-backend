package com.careerzip.domain.question.service;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.answer.service.AnswerService;
import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.QuestionWithAnswers;
import com.careerzip.domain.archiving.entity.Archiving;
import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.careerzip.testobject.answer.AnswerFactory.createAnswers;
import static com.careerzip.testobject.archiving.ArchivingFactory.createArchiving;
import static com.careerzip.testobject.question.QuestionFactory.createQuestions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @InjectMocks
    QuestionService questionService;

    @Mock
    QuestionRepository questionRepository;

    @Mock
    AnswerService answerService;

    @Test
    @DisplayName("특정 Archiving의 QuestionWithAnswers를 반환하는 테스트")
    void findWithAnswersTest() {
        // given
        Archiving archiving = createArchiving();
        List<Question> questions = createQuestions();
        List<Answer> answers = createAnswers();
        List<QuestionWithAnswers> testQuestionWithAnswers = QuestionWithAnswers.listOf(questions, answers);

        // when
        when(questionRepository.findAllBy(any(QuestionPaperForm.class))).thenReturn(questions);
        when(answerService.groupingAnswersBy(archiving, questions)).thenReturn(testQuestionWithAnswers);

        List<QuestionWithAnswers> questionWithAnswers = questionService.findWithAnswers(archiving);

        // then
        assertThat(testQuestionWithAnswers).usingRecursiveComparison().isEqualTo(questionWithAnswers);
    }
}
