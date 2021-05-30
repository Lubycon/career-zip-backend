package com.careerzip.domain.project.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.answer.repository.AnswerRepository;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ProjectSummary;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.QuestionWithAnswers;
import com.careerzip.domain.project.dto.response.ProjectSummaryResponse;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.domain.project.repository.ProjectRepository;
import com.careerzip.domain.question.entity.Question;
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
import java.util.Set;
import java.util.stream.Collectors;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.account.AccountFactory.createOAuthAccountOf;
import static com.careerzip.testobject.answer.AnswerFactory.createAnswers;
import static com.careerzip.testobject.project.ProjectFactory.createProject;
import static com.careerzip.testobject.question.QuestionFactory.createQuestions;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @InjectMocks
    ProjectService projectService;

    @Mock
    ProjectRepository projectRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    AnswerRepository answerRepository;

    @Test
    @DisplayName("QuestionWithAnswers 를 받아서 선택된 Project를 꺼내는 메서드 테스트")
    void findSelectedProjectsByTest() {
        // given
        List<Question> questions = createQuestions();
        List<Answer> answers = createAnswers();
        List<QuestionWithAnswers> questionWithAnswers = QuestionWithAnswers.listOf(questions, answers);
        List<Project> projects = answers.stream()
                                        .map(Answer::getProject)
                                        .collect(Collectors.toList());
        Set<Long> projectIds = projects.stream()
                                       .map(Project::getId)
                                       .collect(Collectors.toSet());

        // when
        Set<ProjectSummary> selectedProjects = projectService.findSelectedProjectsBy(questionWithAnswers);

        // then
        assertThat(selectedProjects.size()).isEqualTo(projectIds.size());
    }

    @Test
    @DisplayName("Account 기준으로 모든 Project 리스트를 찾아서 반환하는 테스트")
    void findAllByAccountTest() {
        // given
        Account account = createMember();
        OAuthAccount loginAccount = createOAuthAccountOf(account);
        List<Project> projects = Arrays.asList(createProject(), createProject(), createProject());

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));
        when(projectRepository.findAllByAccount(account)).thenReturn(projects);

        List<ProjectSummaryResponse> response = projectService.findAll(loginAccount);

        // then
        assertThat(response.size()).isEqualTo(projects.size());
    }
}
