package com.careerzip.domain.project.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.answer.repository.AnswerRepository;
import com.careerzip.domain.archive.dto.request.createarchiverequest.CreateAnswerDetail;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ProjectSummary;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.QuestionWithAnswers;
import com.careerzip.domain.project.dto.request.CreateProjectRequest;
import com.careerzip.domain.project.dto.response.ProjectDetail;
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

import java.util.*;
import java.util.stream.Collectors;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.account.AccountFactory.createOAuthAccountOf;
import static com.careerzip.testobject.answer.AnswerFactory.createAnswers;
import static com.careerzip.testobject.answer.AnswerFactory.createCreateAnswerDetailsOf;
import static com.careerzip.testobject.project.ProjectFactory.createProject;
import static com.careerzip.testobject.project.ProjectFactory.createProjectRequest;
import static com.careerzip.testobject.question.QuestionFactory.createQuestions;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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

    @Test
    @DisplayName("Answer와 연관된 Project들을 가져와서 Map으로 만드는 테스트")
    void findAllMapByAnswersTest() {
        // given
        List<Question> questions = createQuestions();
        Project project = createProject();
        List<CreateAnswerDetail> answerDetails = createCreateAnswerDetailsOf(questions, project);
        List<Project> projects = List.of(project);

        // when
        when(projectRepository.findAllByIds(anyList())).thenReturn(projects);

        Map<Long, Project> projectsMap = projectService.findAllMapBy(answerDetails);

        // then
        assertThat(projectsMap.keySet()).containsAll(projects.stream()
                                                             .map(Project::getId)
                                                             .collect(Collectors.toList()));
        assertThat(projectsMap.size()).isEqualTo(projects.size());
    }

    @Test
    @DisplayName("Project를 조회해서 ProjectDetail DTO로 반환하는 테스트")
    void findByTest() {
        // given
        Account account = createMember();
        OAuthAccount loginAccount = createOAuthAccountOf(account);
        Project project = createProject();

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));
        when(projectRepository.findBy(account, project.getId())).thenReturn(Optional.of(project));

        ProjectDetail projectDetail = projectService.findBy(loginAccount, project.getId());

        // then
        assertThat(projectDetail.getId()).isEqualTo(project.getId());
        assertThat(projectDetail.getTitle()).isEqualTo(project.getTitle());
    }

    @Test
    @DisplayName("새로운 Project를 생성하는 테스트")
    void createByTest() {
        // given
        Account account = createMember();
        OAuthAccount loginAccount = createOAuthAccountOf(account);
        CreateProjectRequest request = createProjectRequest();
        Project project = request.toEntity(account);

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        Project newProject = projectService.createBy(loginAccount, request);

        // then
        assertThat(newProject).usingRecursiveComparison().isEqualTo(project);
    }
}
