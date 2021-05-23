package com.careerzip.testobject.answer;

import com.careerzip.domain.project.entity.Project;
import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.AnswerDetail;
import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.project.dto.response.ProjectDetail;

import static com.careerzip.testobject.project.ProjectFactory.createProject;

public class AnswerFactory {

    // Answer
    public static Answer createAnswer() {
        return Answer.builder()
                     .id(1L)
                     .comment("Answer comment")
                     .project(createProject())
                     .build();
    }


    // AnswerDetail
    public static AnswerDetail createAnswerDetail() {
        Answer answer = createAnswer();
        Project project = createProject();

        return AnswerDetail.builder()
                           .comment(answer.getComment())
                           .hashtag(ProjectDetail.from(project))
                           .build();
    }
}
