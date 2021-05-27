package com.careerzip.domain.archive.dto.response.archivedetailresponse;

import com.careerzip.domain.answer.entity.Answer;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AnswerDetail {

    private final long id;

    @NotNull
    private final String comment;

    private final boolean important;

    @NotNull
    private final ProjectSummary project;

    @Builder
    private AnswerDetail(long id, String comment, boolean important, ProjectSummary project) {
        this.id = id;
        this.comment = comment;
        this.important = important;
        this.project = project;
    }

    public static AnswerDetail from(Answer answer) {
        return AnswerDetail.builder()
                .id(answer.getId())
                .comment(answer.getComment())
                .important(answer.getImportant())
                .project(ProjectSummary.from(answer.getProject()))
                .build();
    }

    public static List<AnswerDetail> listOf(List<Answer> answers) {
        return answers.stream()
                      .map(AnswerDetail::from)
                      .collect(Collectors.toList());
    }
}
