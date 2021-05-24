package com.careerzip.testobject.answer;

import com.careerzip.domain.project.entity.Project;
import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.AnswerDetail;
import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.project.dto.response.ProjectDetail;
import com.careerzip.domain.question.entity.Question;

import static com.careerzip.testobject.project.ProjectFactory.createProject;
import static com.careerzip.testobject.question.QuestionFactory.createQuestion;

public class AnswerFactory {

    // Answer
    public static Answer createAnswer() {
        return Answer.builder()
                     .id(1L)
                     .comment("Answer comment")
                     .question(createQuestion())
                     .project(createProject())
                     .build();
    }

    public static Answer createAnswerOf(Project project) {
        return Answer.builder()
                     .id(1L)
                     .comment("Answer comment")
                     .question(createQuestion())
                     .project(project)
                     .build();
    }

    public static Answer createAnswerOf(Question question, Project project) {
        return Answer.builder()
                .id(1L)
                .comment("Answer comment")
                .question(question)
                .project(project)
                .build();
    }

    // AnswerDetail
    public static AnswerDetail createAnswerDetail() {
        Answer answer = createAnswer();
        Project project = createProject();

        return AnswerDetail.builder()
                           .comment(answer.getComment())
                           .project(ProjectDetail.from(project))
                           .build();
    }
}
