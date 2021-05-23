package com.careerzip.domain.archiving.dto.response.archivingdetailresponse;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.project.dto.response.ProjectDetail;
import com.careerzip.domain.project.entity.Project;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public class AnswerDetail {

    @Nullable
    private final String comment;

    @Nullable
    private final ProjectDetail hashtag;

    @Builder
    private AnswerDetail(String comment, ProjectDetail hashtag) {
        this.comment = comment;
        this.hashtag = hashtag;
    }

    // TODO: Hashtag null 값 반환에 대한 처리
    public static AnswerDetail of(Answer answer, Project project) {
        return AnswerDetail.builder()
                           .comment(answer.getComment())
                           .hashtag(ProjectDetail.from(project))
                           .build();
    }
}
