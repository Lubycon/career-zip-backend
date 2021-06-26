package com.careerzip.global.admin.dto.response;

import com.careerzip.domain.account.dto.response.AccountSummary;
import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archive.dto.response.archivesresponse.RelatedProject;
import com.careerzip.domain.archive.entity.Archive;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ArchiveRelatedData {

    private final long id;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH시 mm분")
    private final LocalDateTime createdDateTime;

    @NotNull
    private final List<RelatedProject> projects;

    @NotNull
    private final AccountSummary account;

    @Builder
    private ArchiveRelatedData(long id, LocalDateTime createdDateTime, List<RelatedProject> projects, AccountSummary account) {
        this.id = id;
        this.createdDateTime = createdDateTime;
        this.projects = projects;
        this.account = account;
    }

    public static ArchiveRelatedData of(Archive archive, List<RelatedProject> projects, Account account) {
        return ArchiveRelatedData.builder()
                                 .id(archive.getId())
                                 .createdDateTime(archive.getCreatedDateTime())
                                 .projects(projects)
                                 .account(AccountSummary.from(account))
                                 .build();
    }

    public static List<ArchiveRelatedData> listOf(Page<Archive> archivePage, Set<RelatedProject> projects) {
        List<Archive> archives = archivePage.getContent();
        Map<Long, List<RelatedProject>> projectsMap = projects.stream()
                                                              .collect(Collectors.groupingBy(RelatedProject::getArchiveId));

        return archives.stream()
                       .map(archive -> {
                           Account account = archive.getAccount();
                           List<RelatedProject> relatedProjects = projectsMap.get(archive.getId());
                           return of(archive, relatedProjects, account);
                       }).collect(Collectors.toList());
    }
}
