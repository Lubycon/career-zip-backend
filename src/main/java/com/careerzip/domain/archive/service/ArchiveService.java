package com.careerzip.domain.archive.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.account.repository.AccountRepository;
import com.careerzip.domain.answer.service.AnswerService;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ArchiveDetailResponse;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ProjectSummary;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.QuestionWithAnswers;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.project.service.ProjectService;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.service.QuestionService;
import com.careerzip.domain.archive.dto.response.archivingsresponse.ArchivingSummary;
import com.careerzip.domain.archive.dto.response.archivingsresponse.ArchivingsResponse;
import com.careerzip.domain.archive.repository.ArchiveRepository;
import com.careerzip.global.error.exception.entity.AccountNotFoundException;
import com.careerzip.global.error.exception.entity.ArchiveNotFoundException;
import com.careerzip.global.pagination.CustomPageRequest;
import com.careerzip.global.pagination.Pagination;
import com.careerzip.security.oauth.dto.OAuthAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ArchiveService {

    private final ArchiveRepository archiveRepository;
    private final AccountRepository accountRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final ProjectService projectService;

    public ArchivingsResponse findAll(OAuthAccount loginAccount, Pagination pagination) {
        PageRequest pageRequest = CustomPageRequest.of(pagination);
        Account account = accountRepository.findById(loginAccount.getId()).orElseThrow(AccountNotFoundException::new);
        Page<Archive> archivingPage = archiveRepository.findAllBy(account, pageRequest);
        List<ArchivingSummary> archivings = ArchivingSummary.listOf(archivingPage);
        return ArchivingsResponse.of(archivingPage, archivings);
    }

    public ArchiveDetailResponse findBy(OAuthAccount loginAccount, Long archivingId) {
        Account account = accountRepository.findById(loginAccount.getId()).orElseThrow();
        Archive archive = archiveRepository.findBy(account, archivingId).orElseThrow(ArchiveNotFoundException::new);
        List<Question> questions = questionService.findAllBy(archive);
        List<QuestionWithAnswers> questionWithAnswers = answerService.groupingAnswersBy(archive, questions);
        Set<ProjectSummary> selectedProjects = projectService.findSelectedProjectsBy(questionWithAnswers);
        return ArchiveDetailResponse.of(archive, selectedProjects, questionWithAnswers);
    }
}
