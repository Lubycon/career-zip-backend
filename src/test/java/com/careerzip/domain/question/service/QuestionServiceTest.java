package com.careerzip.domain.question.service;

import com.careerzip.domain.answer.service.AnswerService;
import com.careerzip.domain.archive.dto.request.createarchiverequest.CreateAnswerDetail;
import com.careerzip.domain.archive.dto.request.createarchiverequest.CreateArchiveRequest;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.project.entity.Project;
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
import java.util.Map;
import java.util.stream.Collectors;

import static com.careerzip.testobject.answer.AnswerFactory.*;
import static com.careerzip.testobject.archive.ArchiveFactory.*;
import static com.careerzip.testobject.project.ProjectFactory.createProject;
import static com.careerzip.testobject.question.QuestionFactory.createQuestions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
    @DisplayName("특정 Archive의 Questions를 반환하는 테스트")
    void findWithAnswersTest() {
        // given
        Archive archive = createArchive();
        List<Question> testQuestions = createQuestions();

        // when
        when(questionRepository.findAllBy(any(QuestionPaperForm.class))).thenReturn(testQuestions);

        List<Question> questions = questionService.findAllBy(archive);

        // then
        assertThat(questions).usingRecursiveComparison().isEqualTo(testQuestions);
    }

    @Test
    @DisplayName("Answer와 연관된 Question들을 가져와서 Map으로 만드는 테스트")
    void findAllMapByAnswersTest() {
        // given
        Project project = createProject();
        List<Question> questions = createQuestions();
        List<CreateAnswerDetail> answerDetails = createCreateAnswerDetailsOf(questions, project);

        // when
        when(questionRepository.findAllByIds(anyList())).thenReturn(questions);

        Map<Long, Question> questionsMap = questionService.findAllMapBy(answerDetails);

        // then
        assertThat(questionsMap.keySet()).containsAll(questions.stream()
                                                               .map(Question::getId)
                                                               .collect(Collectors.toList()));
        assertThat(questionsMap.size()).isEqualTo(questions.size());
    }
}
