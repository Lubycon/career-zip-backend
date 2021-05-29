package com.careerzip.domain.answer.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.answer.dto.request.PreviousAnswersRequest;
import com.careerzip.domain.answer.dto.request.PreviousAnswersRequestBuilder;
import com.careerzip.domain.answer.dto.response.PreviousAnswersWithQuestion;
import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.answer.repository.AnswerRepository;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.QuestionWithAnswers;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.repository.QuestionRepository;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaper.repository.QuestionPaperRepository;
import com.careerzip.security.oauth.dto.OAuthAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.account.AccountFactory.createOAuthAccountOf;
import static com.careerzip.testobject.answer.AnswerFactory.createAnswers;
import static com.careerzip.testobject.archive.ArchiveFactory.createArchive;
import static com.careerzip.testobject.question.QuestionFactory.createQuestions;
import static com.careerzip.testobject.questionpaper.QuestionPaperFactory.createQuestionPaper;
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

    @Mock
    QuestionRepository questionRepository;

    @Mock
    QuestionPaperRepository questionPaperRepository;

    @Test
    @DisplayName("Questions에 대한 Answers를 그룹핑 한 뒤 반환하는 테스트")
    void groupingAnswersByTest() {
        // given
        Archive archive = createArchive();
        List<Question> questions = createQuestions();
        List<Answer> testAnswers = createAnswers();

        // when
        when(answerRepository.findAllBy(any(Archive.class), anyList())).thenReturn(testAnswers);

        List<QuestionWithAnswers> questionWithAnswers = answerService.groupingAnswersBy(archive, questions);

        // then
        assertThat(questionWithAnswers.size()).isEqualTo(questions.size());
    }

    @Test
    @DisplayName("Projects를 기준으로 이전 Answer 리스트를 반환하는 테스트")
    void findAllPreviousByProjectsTest() {
        // given
        Account account = createMember();
        OAuthAccount loginAccount = createOAuthAccountOf(account);
        QuestionPaper questionPaper = createQuestionPaper();
        List<Question> questions = createQuestions();
        List<Answer> answers = createAnswers();
        List<Long> answerIds = answers.stream()
                                      .map(Answer::getId)
                                      .collect(Collectors.toList());

        List<Long> projectIds = answers.stream()
                                       .map(answer -> answer.getProject().getId())
                                       .collect(Collectors.toList());
        PreviousAnswersRequest request = PreviousAnswersRequestBuilder.newBuilder()
                                                                      .projectIds(projectIds)
                                                                      .build();

        // when
        when(questionPaperRepository.findLatest()).thenReturn(Optional.of(questionPaper));
        when(questionRepository.findAllBy(questionPaper.getQuestionPaperForm())).thenReturn(questions);
        when(answerRepository.findAllPreviousIdsBy(loginAccount.getId(), request.getProjectIds(), questions)).thenReturn(answerIds);
        when(answerRepository.findAllBy(answerIds)).thenReturn(answers);

        List<PreviousAnswersWithQuestion> answersWithQuestions = answerService.findAllPreviousBy(loginAccount, request);

        // then
        assertThat(answersWithQuestions.size()).isEqualTo(questions.size());
        assertThat(answersWithQuestions.stream()
                                        .map(PreviousAnswersWithQuestion::getAnswers)
                                        .count()).isEqualTo(answers.size());
    }
}
