package com.careerzip.global.admin.service;

import com.careerzip.domain.answer.service.AnswerService;
import com.careerzip.domain.archive.dto.response.archivesresponse.RelatedProject;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.archive.repository.ArchiveRepository;
import com.careerzip.domain.project.service.ProjectService;
import com.careerzip.domain.question.service.QuestionService;
import com.careerzip.domain.questionpaper.repository.QuestionPaperRepository;
import com.careerzip.global.admin.dto.response.AdminArchivesResponse;
import com.careerzip.global.admin.dto.response.ArchiveWithAccount;
import com.careerzip.global.pagination.CustomPageRequest;
import com.careerzip.global.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final ArchiveRepository archiveRepository;
    private final ProjectService projectService;

    public AdminArchivesResponse findAllArchives(Pagination pagination, LocalDate startDate, LocalDate endDate) {
        PageRequest pageRequest = CustomPageRequest.of(pagination);
        Page<Archive> archivePage = archiveRepository.findAllBy(startDate, endDate, pageRequest);
        Set<RelatedProject> projects = projectService.findAllRelatedBy(archivePage);
        List<ArchiveWithAccount> archives = ArchiveWithAccount.listOf(archivePage, projects);
        return AdminArchivesResponse.of(archivePage, archives);
    }
}
