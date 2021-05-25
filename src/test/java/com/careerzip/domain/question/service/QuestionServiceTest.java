package com.careerzip.domain.question.service;

import com.careerzip.domain.answer.service.AnswerService;
import com.careerzip.domain.question.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

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


        // when

        // then
    }
}
