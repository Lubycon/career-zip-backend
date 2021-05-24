package com.careerzip.domain.archiving.dto.response.archivingdetailresponse;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.project.dto.response.ProjectDetail;
import com.careerzip.domain.project.entity.Project;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AnswerDetail {

    @Nullable
    private final String comment;

    @Nullable
    private final ProjectDetail project;

    @Builder
    private AnswerDetail(String comment, ProjectDetail project) {
        this.comment = comment;
        this.project = project;
    }

    public static AnswerDetail from(Answer answer) {
        AnswerDetailBuilder builder = AnswerDetail.builder()
                                                  .comment(answer.getComment());

        Project project = answer.getProject();

        if (project == null) {
            return builder.build();
        }

        return builder.project(ProjectDetail.from(project))
                      .build();
    }

    public static List<AnswerDetail> listOf(List<Answer> answers) {
        return answers.stream()
                      .map(AnswerDetail::from)
                      .collect(Collectors.toList());
    }
}
