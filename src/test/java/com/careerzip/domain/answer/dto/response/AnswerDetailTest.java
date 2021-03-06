package com.careerzip.domain.answer.dto.response;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.AnswerDetail;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ProjectSummary;
import com.careerzip.domain.project.dto.response.ProjectDetail;
import com.careerzip.domain.project.entity.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.careerzip.testobject.answer.AnswerFactory.createAnswerOf;
import static com.careerzip.testobject.project.ProjectFactory.createProject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AnswerDetailTest {

    @Test
    @DisplayName("Project not null - AnswerDetail 생성 테스트")
    void createAnswerDetailWhenProjectNotNullTest() {
        // given
        Project project = createProject();
        Answer answer = createAnswerOf(project);

        // when
        AnswerDetail answerDetail = AnswerDetail.from(answer);
        ProjectSummary projectSummary = answerDetail.getProject();

        // then
        assertAll(
                () -> assertThat(answerDetail.getId()).isEqualTo(answer.getId()),
                () -> assertThat(answerDetail.getComment()).isEqualTo(answer.getComment()),
                () -> assertThat(answerDetail.isImportant()).isEqualTo(answer.getImportant()),
                () -> assertThat(projectSummary.getId()).isEqualTo(project.getId()),
                () -> assertThat(projectSummary.getTitle()).isEqualTo(project.getTitle())
        );
    }
}
