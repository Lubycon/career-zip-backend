package com.careerzip.domain.project.service;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.ProjectSummary;
import com.careerzip.domain.archive.dto.response.archivedetailresponse.QuestionWithAnswers;
import com.careerzip.domain.project.entity.Project;
import com.careerzip.domain.question.entity.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.careerzip.testobject.answer.AnswerFactory.createAnswers;
import static com.careerzip.testobject.question.QuestionFactory.createQuestions;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @InjectMocks
    ProjectService projectService;

    @Test
    @DisplayName("QuestionWithAnswers 를 받아서 선택된 Project를 꺼내는 메서드 테스트")
    void findSelectedProjectsByTest() {
        // given
        List<Question> questions = createQuestions();
        List<Answer> answers = createAnswers();
        List<QuestionWithAnswers> questionWithAnswers = QuestionWithAnswers.listOf(questions, answers);
        List<Project> projects = answers.stream()
                                        .map(Answer::getProject)
                                        .collect(Collectors.toList());
        Set<Long> projectIds = projects.stream()
                                       .map(Project::getId)
                                       .collect(Collectors.toSet());

        // when
        Set<ProjectSummary> selectedProjects = projectService.findSelectedProjectsBy(questionWithAnswers);

        // then
        assertThat(selectedProjects.size()).isEqualTo(projectIds.size());
    }
}
