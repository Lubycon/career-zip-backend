package com.careerzip.testobject.answer;

import com.careerzip.domain.project.entity.Project;
import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.AnswerDetail;
import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.project.dto.response.ProjectDetail;
import com.careerzip.domain.question.entity.Question;

import java.util.Arrays;
import java.util.List;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.archiving.ArchivingFactory.createArchiving;
import static com.careerzip.testobject.project.ProjectFactory.createProject;
import static com.careerzip.testobject.question.QuestionFactory.createQuestion;

public class AnswerFactory {

    // Answer
    public static Answer createAnswer() {
        return Answer.builder()
                     .id(1L)
                     .comment("Answer comment")
                     .archiving(createArchiving())
                     .question(createQuestion())
                     .project(createProject())
                     .account(createMember())
                     .build();
    }

    public static Answer createAnswerOf(Project project) {
        return Answer.builder()
                     .id(1L)
                     .comment("Answer comment")
                     .archiving(createArchiving())
                     .question(createQuestion())
                     .project(project)
                     .account(createMember())
                     .build();
    }

    public static Answer createAnswerOf(Question question, Project project) {
        return Answer.builder()
                .id(1L)
                .comment("Answer comment")
                .archiving(createArchiving())
                .question(question)
                .project(project)
                .account(createMember())
                .build();
    }

    // Answers
    public static List<Answer> createAnswers() {
        return Arrays.asList(Answer.builder()
                                   .id(1L)
                                   .comment("Answer comment")
                                   .archiving(createArchiving())
                                   .question(createQuestion())
                                   .project(createProject())
                                   .account(createMember())
                                   .build(),
                            Answer.builder()
                                  .id(2L)
                                  .comment("Answer comment")
                                  .archiving(createArchiving())
                                  .question(createQuestion())
                                  .project(createProject())
                                  .account(createMember())
                                  .build(),
                            Answer.builder()
                                  .id(3L)
                                  .comment("Answer comment")
                                  .archiving(createArchiving())
                                  .question(createQuestion())
                                  .project(createProject())
                                  .account(createMember())
                                  .build());
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
