package com.careerzip.domain.answer.dto.response;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.domain.question.entity.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PreviousAnswer {

    private final long id;

    @NotNull
    private final String comment;

    private final boolean important;

    @NotNull
    private final ProjectSummary project;

    @Builder
    private PreviousAnswer(long id, String comment, boolean important, ProjectSummary project) {
        this.id = id;
        this.comment = comment;
        this.important = important;
        this.project = project;
    }

    public static PreviousAnswer from(Answer answer) {
        Project project = answer.getProject();

        return PreviousAnswer.builder()
                             .id(answer.getId())
                             .comment(answer.getComment())
                             .important(answer.getImportant())
                             .project(ProjectSummary.from(project))
                             .build();
    }
}
