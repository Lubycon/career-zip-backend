package com.careerzip.domain.archive.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.answer.service.AnswerService;
import com.careerzip.domain.archive.dto.request.createarchiverequest.CreateAnswerDetail;
import com.careerzip.domain.archive.dto.request.createarchiverequest.CreateArchiveRequest;
import com.careerzip.domain.archive.dto.response.DeleteArchiveResponse;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ArchiveDetailResponse;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ProjectSummary;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.QuestionWithAnswers;
import com.careerzip.domain.archive.dto.response.archivesresponse.RelatedProject;
import com.careerzip.domain.archive.dto.response.newarchiveresponse.NewArchiveResponse;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.domain.project.service.ProjectService;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.service.QuestionService;
import com.careerzip.domain.archive.dto.response.archivesresponse.ArchiveSummary;
import com.careerzip.domain.archive.dto.response.archivesresponse.ArchivesResponse;
import com.careerzip.domain.archive.repository.ArchiveRepository;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaper.repository.QuestionPaperRepository;
import com.careerzip.global.error.exception.entity.AccountNotFoundException;
import com.careerzip.global.error.exception.entity.ArchiveNotFoundException;
import com.careerzip.global.error.exception.entity.QuestionPaperNotFoundException;
import com.careerzip.global.pagination.CustomPageRequest;
import com.careerzip.global.pagination.Pagination;
import com.careerzip.security.oauth.dto.OAuthAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class ArchiveService {

    private final ArchiveRepository archiveRepository;
    private final AccountRepository accountRepository;
    private final QuestionPaperRepository questionPaperRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final ProjectService projectService;

    @NotNull
    private Account findByOrThrow(long userId) {
        return accountRepository.findById(userId).orElseThrow(AccountNotFoundException::new);
    }

    @Transactional
    public ArchivesResponse findAll(OAuthAccount loginAccount, Pagination pagination) {
        PageRequest pageRequest = CustomPageRequest.of(pagination);
        Account account = accountRepository.findById(loginAccount.getId()).orElseThrow(AccountNotFoundException::new);
        Page<Archive> archivePage = archiveRepository.findAllBy(account, pageRequest);
        Set<RelatedProject> projects = projectService.findAllRelatedBy(archivePage);
        List<ArchiveSummary> archives = ArchiveSummary.listOf(archivePage, projects);
        return ArchivesResponse.of(archivePage, archives);
    }

    @Transactional
    public ArchiveDetailResponse findBy(OAuthAccount loginAccount, Long archiveId) {
        Account account = accountRepository.findById(loginAccount.getId()).orElseThrow(AccountNotFoundException::new);
        Archive archive = archiveRepository.findBy(account, archiveId).orElseThrow(ArchiveNotFoundException::new);
        List<Question> questions = questionService.findAllBy(archive);
        List<QuestionWithAnswers> questionWithAnswers = answerService.groupingAnswersBy(archive, questions);
        Set<ProjectSummary> selectedProjects = projectService.findSelectedProjectsBy(questionWithAnswers);
        return ArchiveDetailResponse.of(archive, selectedProjects, questionWithAnswers);
    }

    @Transactional
    public NewArchiveResponse createBy(OAuthAccount loginAccount, CreateArchiveRequest request) {
        Account account = accountRepository.findById(loginAccount.getId()).orElseThrow(AccountNotFoundException::new);
        QuestionPaper questionPaper = questionPaperRepository.findById(request.getQuestionPaperId())
                                                             .orElseThrow(QuestionPaperNotFoundException::new);
        Archive newArchive = archiveRepository.save(Archive.of(questionPaper, account));

        List<CreateAnswerDetail> answerDetails = request.getAnswers();
        Map<Long, Question> questionsMap = questionService.findAllMapBy(answerDetails);
        Map<Long, Project> projectsMap = projectService.findAllMapBy(answerDetails);

        answerService.createBy(answerDetails, questionsMap, projectsMap, newArchive, account);
        Account updatedAccount = account.addSubmitCount();

        return NewArchiveResponse.of(newArchive, updatedAccount);
    }

    // 지금은 페이지네이션이 없었군! 30개 디폴트로 낭낭하게 잡아두기
    @Transactional
    public DeleteArchiveResponse delete(long userId, @NotNull List<Long> deleteArchiveIds) {
        Account account = findByOrThrow(userId);
        int deletedCount = archiveRepository.deleteByIdIn(deleteArchiveIds);
        Page<Archive> archivePages = archiveRepository.findAllBy(
                account,
                CustomPageRequest.of(Pagination.defaultPagination())
        );
        Set<RelatedProject> relatedProjects = projectService.findAllRelatedBy(archivePages);
        List<ArchiveSummary> archives = ArchiveSummary.listOf(archivePages, relatedProjects);
        DeleteArchiveResponse deleteArchiveResponse = new DeleteArchiveResponse(
                deletedCount,
                ArchivesResponse.of(archivePages, archives)
        );

        log.info("DeleteArchiveResponse for userId : {} -> {}", userId, deleteArchiveResponse);
        return deleteArchiveResponse;
    }
}
