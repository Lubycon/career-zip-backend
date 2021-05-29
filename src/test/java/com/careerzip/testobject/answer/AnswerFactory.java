package com.careerzip.testobject.answer;

import com.careerzip.domain.account.entity.Account;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.AnswerDetail;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ProjectSummary;
import com.careerzip.domain.archive.entity.Archive;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.question.entity.Question;

import java.util.Arrays;
import java.util.List;

import static com.careerzip.testobject.account.AccountFactory.createMember;
import static com.careerzip.testobject.archive.ArchiveFactory.createArchive;
import static com.careerzip.testobject.project.ProjectFactory.createProject;
import static com.careerzip.testobject.question.QuestionFactory.createQuestion;

public class AnswerFactory {

    // Answer
    public static Answer createAnswer() {
        return Answer.builder()
                     .id(1L)
                     .comment("Answer comment")
                     .important(true)
                     .shared(false)
                     .archive(createArchive())
                     .question(createQuestion())
                     .project(createProject())
                     .account(createMember())
                     .build();
    }

    public static Answer createAnswerOf(Project project) {
        return Answer.builder()
                     .id(1L)
                     .comment("Answer comment")
                     .important(true)
                     .shared(false)
                     .archive(createArchive())
                     .question(createQuestion())
                     .project(project)
                     .account(createMember())
                     .build();
    }

    public static Answer createAnswerOf(Question question, Project project) {
        return Answer.builder()
                     .id(1L)
                     .comment("Answer comment")
                     .important(true)
                     .shared(false)
                     .archive(createArchive())
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
                                   .important(true)
                                   .shared(false)
                                   .archive(createArchive())
                                   .question(createQuestion())
                                   .project(createProject())
                                   .account(createMember())
                                   .build(),
                            Answer.builder()
                                  .id(2L)
                                  .comment("Answer comment")
                                  .important(true)
                                  .shared(false)
                                  .archive(createArchive())
                                  .question(createQuestion())
                                  .project(createProject())
                                  .account(createMember())
                                  .build(),
                            Answer.builder()
                                  .id(3L)
                                  .comment("Answer comment")
                                  .important(true)
                                  .shared(false)
                                  .archive(createArchive())
                                  .question(createQuestion())
                                  .project(createProject())
                                  .account(createMember())
                                  .build());
    }

    public static Answer createJpaAnswerOf(Project project, Question question, Archive archive, Account account) {
        return Answer.builder()
                     .comment("Answer Comment")
                     .important(false)
                     .shared(false)
                     .project(project)
                     .question(question)
                     .archive(archive)
                     .account(account)
                     .build();
    }

    // AnswerDetail
    public static AnswerDetail createAnswerDetail() {
        Answer answer = createAnswer();
        Project project = createProject();

        return AnswerDetail.builder()
                           .comment(answer.getComment())
                           .project(ProjectSummary.from((project)))
                           .build();
    }
}
