package com.careerzip.domain.answer.dto.response;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.careerarchive.dto.response.archivingdetailresponse.AnswerDetail;
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
        ProjectDetail projectDetail = answerDetail.getProject();

        // then
        assertAll(
                () -> assertThat(answerDetail.getComment()).isEqualTo(answer.getComment()),
                () -> assertThat(projectDetail.getId()).isEqualTo(project.getId()),
                () -> assertThat(projectDetail.getTitle()).isEqualTo(project.getTitle())
        );
    }

    @Test
    @DisplayName("Project null - AnswerDetail 생성 테스트")
    void createAnswerDetailWhenProjectNullTest() {
        // given
        Answer answer = createAnswerOf(null);

        // when
        AnswerDetail answerDetail = AnswerDetail.from(answer);
        ProjectDetail projectDetail = answerDetail.getProject();

        // then
        assertThat(answerDetail.getComment()).isEqualTo(answer.getComment());
        assertThat(projectDetail).isNull();
    }
}
