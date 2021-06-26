package com.careerzip.global.admin.dto.response;

import com.careerzip.domain.account.dto.response.AccountSummary;
import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ProjectSummary;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.QuestionWithAnswers;
import com.careerzip.domain.archive.entity.Archive;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
public class AdminArchiveResponse {

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy년 MM월 dd일 HH시 mm분")
    private final LocalDateTime createdDateTime;

    @NotNull
    private final Set<ProjectSummary> projects;

    @NotNull
    private final AccountSummary account;

    @NotNull
    private final List<QuestionWithAnswers> questionWithAnswers;

    @Builder
    private AdminArchiveResponse(LocalDateTime createdDateTime, Set<ProjectSummary> projects, AccountSummary account,
                                 List<QuestionWithAnswers> questionWithAnswers) {
        this.createdDateTime = createdDateTime;
        this.projects = projects;
        this.account = account;
        this.questionWithAnswers = questionWithAnswers;
    }

    public static AdminArchiveResponse of(Archive archive, Set<ProjectSummary> projects, Account account,
                                          List<QuestionWithAnswers> questionWithAnswers) {
        return AdminArchiveResponse.builder()
                                   .createdDateTime(archive.getCreatedDateTime())
                                   .projects(projects)
                                   .account(AccountSummary.from(account))
                                   .questionWithAnswers(questionWithAnswers)
                                   .build();
    }
}
