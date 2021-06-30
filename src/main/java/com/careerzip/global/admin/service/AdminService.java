package com.careerzip.global.admin.service;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.answer.service.AnswerService;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ProjectSummary;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.QuestionWithAnswers;
import com.careerzip.domain.archive.dto.response.archivesresponse.RelatedProject;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.archive.repository.ArchiveRepository;
import com.careerzip.domain.project.service.ProjectService;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.question.service.QuestionService;
import com.careerzip.domain.questionpaper.entity.QuestionPaper;
import com.careerzip.domain.questionpaper.repository.QuestionPaperRepository;
import com.careerzip.domain.questionpaperform.entity.QuestionPaperForm;
import com.careerzip.domain.questionpaperform.repository.QuestionPaperFormRepository;
import com.careerzip.global.admin.dto.request.CreateQuestionPaperRequest;
import com.careerzip.global.admin.dto.request.DateParameters;
import com.careerzip.global.admin.dto.request.UpdateQuestionPaperRequest;
import com.careerzip.global.admin.dto.response.AdminArchiveResponse;
import com.careerzip.global.admin.dto.response.AdminArchivesResponse;
import com.careerzip.global.admin.dto.response.AdminQuestionPaperDetail;
import com.careerzip.global.admin.dto.response.ArchiveRelatedData;
import com.careerzip.global.error.exception.entity.ArchiveNotFoundException;
import com.careerzip.global.error.exception.entity.QuestionPaperNotFoundException;
import com.careerzip.global.pagination.CustomPageRequest;
import com.careerzip.global.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final ArchiveRepository archiveRepository;
    private final QuestionPaperRepository questionPaperRepository;
    private final QuestionPaperFormRepository questionPaperFormRepository;
    private final ProjectService projectService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public AdminArchivesResponse findAllArchives(Pagination pagination, DateParameters dateParameters) {
        PageRequest pageRequest = CustomPageRequest.of(pagination);
        Page<Archive> archivePage = archiveRepository.findAllBy(dateParameters.getStartDate(), dateParameters.getEndDate(), pageRequest);
        Set<RelatedProject> projects = projectService.findAllRelatedBy(archivePage);
        List<ArchiveRelatedData> archives = ArchiveRelatedData.listOf(archivePage, projects);
        return AdminArchivesResponse.of(archivePage, archives);
    }

    public AdminArchiveResponse findArchiveBy(Long archiveId) {
        Archive archive = archiveRepository.findBy(archiveId).orElseThrow(ArchiveNotFoundException::new);
        List<Question> questions = questionService.findAllBy(archive);
        List<QuestionWithAnswers> questionWithAnswers = answerService.groupingAnswersBy(archive, questions);
        Set<ProjectSummary> projects = projectService.findSelectedProjectsBy(questionWithAnswers);
        Account account = archive.getAccount();
        return AdminArchiveResponse.of(archive, projects, account, questionWithAnswers);
    }

    public List<AdminQuestionPaperDetail> findAllQuestionPapers() {
        List<QuestionPaper> questionPapers = questionPaperRepository.findAllByOrderByIdDesc();
        return AdminQuestionPaperDetail.listOf(questionPapers);
    }

    @Transactional
    public AdminQuestionPaperDetail createQuestionPaper(CreateQuestionPaperRequest request) {
        QuestionPaper questionPaper = questionPaperRepository.findLatest().orElseThrow(QuestionPaperNotFoundException::new);
        questionPaper.finishPaper();
        QuestionPaperForm questionPaperForm = questionPaperFormRepository.findFirstByOrderByIdDesc();
        QuestionPaper newQuestionPaper = questionPaperRepository.save(request.toEntity(questionPaperForm));
        return AdminQuestionPaperDetail.from(newQuestionPaper);
    }

    @Transactional
    public AdminQuestionPaperDetail updateQuestionPaper(Long questionPaperId, UpdateQuestionPaperRequest request) {
        QuestionPaper questionPaper = questionPaperRepository.findById(questionPaperId).orElseThrow(QuestionPaperNotFoundException::new);
        QuestionPaper updatedQuestionPaper = request.toEntity(questionPaper);
        return AdminQuestionPaperDetail.from(updatedQuestionPaper);
    }
}
