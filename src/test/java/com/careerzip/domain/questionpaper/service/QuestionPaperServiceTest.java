package com.careerzip.domain.questionpaper.service;

import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.repository.QuestionRepository;
import com.careerzip.domain.questionpaper.dto.LatestQuestionPaperResponse;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaper.repository.QuestionPaperRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.careerzip.testobject.question.QuestionFactory.createQuestions;
import static com.careerzip.testobject.questionpaper.QuestionPaperFactory.createQuestionPaper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionPaperServiceTest {

    @InjectMocks
    QuestionPaperService questionPaperService;

    @Mock
    QuestionPaperRepository questionPaperRepository;

    @Mock
    QuestionRepository questionRepository;

    @Test
    @DisplayName("Latest QuestionPaper 조회 테스트")
    void findLatestTest() {
        // given
        QuestionPaper questionPaper = createQuestionPaper();
        List<Question> questions = createQuestions();

        // when
        when(questionPaperRepository.findLatest()).thenReturn(Optional.of(questionPaper));
        when(questionRepository.findAllBy(questionPaper.getQuestionPaperForm())).thenReturn(questions);

        LatestQuestionPaperResponse response = questionPaperService.findLatest();

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(questionPaper.getId()),
                () -> assertThat(response.getStartDate()).isEqualTo(questionPaper.getStartDateTime().toLocalDate()),
                () -> assertThat(response.getEndDate()).isEqualTo(questionPaper.getEndDateTime().toLocalDate()),
                () -> assertThat(response.getQuestions().size()).isEqualTo(questions.size())
        );
    }
}
