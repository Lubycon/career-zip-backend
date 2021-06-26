package com.careerzip.global.admin.dto.response;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.QuestionWithAnswers;
import com.careerzip.domain.archive.dto.response.archivesresponse.RelatedProject;
import com.careerzip.domain.archive.entity.Archive;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public class AdminArchiveResponse {

    @NotNull
    private final ArchiveRelatedData archive;

    @NotNull
    private final List<QuestionWithAnswers> questionWithAnswers;

    @Builder
    private AdminArchiveResponse(ArchiveRelatedData archive, List<QuestionWithAnswers> questionWithAnswers) {
        this.archive = archive;
        this.questionWithAnswers = questionWithAnswers;
    }

    public static AdminArchiveResponse of(Archive archive, List<RelatedProject> projects, Account account,
                                          List<QuestionWithAnswers> questionWithAnswers) {
        return AdminArchiveResponse.builder()
                                   .archive(ArchiveRelatedData.of(archive, projects, account))
                                   .questionWithAnswers(questionWithAnswers)
                                   .build();
    }
}
