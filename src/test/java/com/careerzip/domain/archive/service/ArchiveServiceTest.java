package com.careerzip.domain.archive.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.answer.service.AnswerService;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ArchiveDetailResponse;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ProjectSummary;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.QuestionWithAnswers;
import com.careerzip.domain.archive.dto.response.archivesresponse.ArchivesResponse;
import com.careerzip.domain.archive.dto.response.archivesresponse.RelatedProject;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.archive.repository.ArchiveRepository;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.domain.project.service.ProjectService;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.service.QuestionService;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.global.pagination.CustomPageRequest;
import com.careerzip.global.pagination.Pagination;
import com.careerzip.security.oauth.dto.OAuthAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.account.AccountFactory.createOAuthAccountOf;
import static com.careerzip.testobject.answer.AnswerFactory.createAnswers;
import static com.careerzip.testobject.archive.ArchiveFactory.createArchive;
import static com.careerzip.testobject.archive.ArchiveFactory.createArchivePage;
import static com.careerzip.testobject.pagination.PaginationFactory.createPaginationOf;
import static com.careerzip.testobject.project.ProjectFactory.createProject;
import static com.careerzip.testobject.question.QuestionFactory.createQuestions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArchiveServiceTest {

    @InjectMocks
    ArchiveService archiveService;

    @Mock
    ArchiveRepository archiveRepository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    QuestionService questionService;

    @Mock
    AnswerService answerService;

    @Mock
    ProjectService projectService;

    @Test
    @DisplayName("특정 Account가 작성한 Archiving 리스트를 DTO로 반환하는 테스트")
    void findAllTest() {
        // given
        int firstIndex = 0;

        Pagination pagination = createPaginationOf(1, 10, "DESC");
        PageRequest pageRequest = CustomPageRequest.of(pagination);
        Page<Archive> archivePage = createArchivePage();
        QuestionPaper questionPaper = archivePage.getContent().get(firstIndex).getQuestionPaper();
        Project testProject = createProject();
        Archive testArchive = createArchive();
        Set<RelatedProject> projects = Collections.singleton(RelatedProject.of(testProject, testArchive));

        Account account = createMember();
        OAuthAccount loginAccount = OAuthAccount.from(account);

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));
        when(archiveRepository.findAllBy(account, pageRequest)).thenReturn(archivePage);
        when(projectService.findAllRelatedBy(archivePage)).thenReturn(projects);

        ArchivesResponse response = archiveService.findAll(loginAccount, pagination);

        // then
        assertAll(
                () -> assertThat(response.getArchives().size()).isEqualTo(archivePage.getSize()),
                () -> assertThat(response.getArchives()
                                         .stream()
                                         .filter(archive -> archive.getStartDate().equals(questionPaper.getStartDateTime().toLocalDate()))
                                         .count()).isEqualTo(archivePage.getSize()),
                () -> assertThat(response.getArchives()
                                         .stream()
                                         .filter(archive -> archive.getEndDate().equals(questionPaper.getEndDateTime().toLocalDate()))
                                         .count()).isEqualTo(archivePage.getSize())
        );
    }

    @Test
    @DisplayName("특정 Account가 작성한 단일 Archive를 DTO로 반환하는 테스트")
    void findByTest() {
        // given
        Account account = createMember();
        OAuthAccount loginAccount = createOAuthAccountOf(account);
        List<Question> questions = createQuestions();
        List<QuestionWithAnswers> questionWithAnswers = QuestionWithAnswers.listOf(createQuestions(), createAnswers());
        Archive archive = createArchive();
        QuestionPaper questionPaper = archive.getQuestionPaper();
        Long archivingId = archive.getId();
        Set<ProjectSummary> selectedProjects = projectService.findSelectedProjectsBy(questionWithAnswers);

        // when
        when(accountRepository.findById(loginAccount.getId())).thenReturn(Optional.of(account));
        when(archiveRepository.findBy(account, archivingId)).thenReturn(Optional.of(archive));
        when(questionService.findAllBy(archive)).thenReturn(questions);
        when(answerService.groupingAnswersBy(archive, questions)).thenReturn(questionWithAnswers);

        ArchiveDetailResponse response = archiveService.findBy(loginAccount, archivingId);

        // then
        assertAll(
                () -> assertThat(response.getId()).isEqualTo(archive.getId()),
                () -> assertThat(response.getStartDate()).isEqualTo(questionPaper.getStartDateTime().toLocalDate()),
                () -> assertThat(response.getEndDate()).isEqualTo(questionPaper.getEndDateTime().toLocalDate()),
                () -> assertThat(response.getCreatedDateTime()).isEqualTo(archive.getCreatedDateTime()),
                () -> assertThat(response.getSelectedProjects().size()).isEqualTo(selectedProjects.size()),
                () -> assertThat(response.getQuestions()).usingRecursiveComparison().isEqualTo(questionWithAnswers)
        );
    }
}
