package com.careerzip.domain.question.dto.response;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.questionoption.QuestionOption;
import com.careerzip.domain.questionitem.entity.QuestionItem;
import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.QuestionWithAnswers;
import com.careerzip.domain.question.entity.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.careerzip.testobject.answer.AnswerFactory.createAnswer;
import static com.careerzip.testobject.answer.AnswerFactory.createAnswerOf;
import static com.careerzip.testobject.project.ProjectFactory.createProject;
import static com.careerzip.testobject.questionitem.QuestionItemFactory.createCheckboxQuestionItem;
import static com.careerzip.testobject.question.QuestionFactory.createQuestionOf;
import static org.assertj.core.api.Assertions.assertThat;

class QuestionWithAnswersTest {

    @Test
    @DisplayName("QuestionWithAnswers 생성 테스트")
    void createQuestionWithAnswersTest() {
        // given
        QuestionItem questionItem = createCheckboxQuestionItem();
        Question question = createQuestionOf(questionItem);
        List<Answer> answers = Arrays.asList(createAnswer(), createAnswer(), createAnswer());

        // when
        QuestionWithAnswers questionWithAnswers = QuestionWithAnswers.of(question, answers);
        List<QuestionOption> questionOptions = questionItem.getQuestionOptions();

        // then
        assertThat(questionWithAnswers.getAnswerOptions())
                .usingRecursiveComparison()
                .isEqualTo(questionOptions.stream()
                                        .map(QuestionOption::getDescription)
                                        .collect(Collectors.toList()));
        assertThat(questionWithAnswers).hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("QuestionWithAnswers 리스트 생성 테스트")
    void createQuestionWithAnswersListTest() {
        // given
        QuestionItem questionItem = createCheckboxQuestionItem();
        List<Question> questions = Arrays.asList(createQuestionOf(1L, questionItem), createQuestionOf(2L, questionItem), createQuestionOf(3L, questionItem));
        List<Answer> answers = questions.stream()
                                        .map(question -> createAnswerOf(question, createProject()))
                                        .collect(Collectors.toList());
        Map<Long, List<Answer>> answersMap = answers.stream()
                                                    .collect(Collectors.groupingBy(Answer::getQuestionId));


        // when
        List<QuestionWithAnswers> questionWithAnswers = QuestionWithAnswers.listOf(questions, answersMap);

        // then
       assertThat(questionWithAnswers.size()).isEqualTo(answersMap.size());
    }
}