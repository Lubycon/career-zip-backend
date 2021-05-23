package com.careerzip.domain.answer.dto.response;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.project.dto.response.ProjectDetail;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.AnswerDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.careerzip.testobject.answer.AnswerFactory.createAnswer;
import static com.careerzip.testobject.project.ProjectFactory.createProject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AnswerDetailTest {

    @Test
    @DisplayName("AnswerDetail 생성 테스트")
    void createAnswerDetailTest() {
        // given
        Answer answer = createAnswer();
        Project project = createProject();

        // when
        AnswerDetail answerDetail = AnswerDetail.of(answer, project);
        ProjectDetail projectDetail = answerDetail.getHashtag();

        // then
        assertAll(
                () -> assertThat(answerDetail.getComment()).isEqualTo(answer.getComment()),
                () -> assertThat(projectDetail.getId()).isEqualTo(project.getId()),
                () -> assertThat(projectDetail.getTitle()).isEqualTo(project.getTitle())
        );
    }
}
