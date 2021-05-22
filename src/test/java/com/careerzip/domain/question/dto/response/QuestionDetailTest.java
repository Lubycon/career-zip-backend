package com.careerzip.domain.question.dto.response;

import com.careerzip.domain.record.dto.response.recorddetailresponse.AnswerDetail;
import com.careerzip.domain.answeroption.AnswerOption;
import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.record.dto.response.recorddetailresponse.QuestionDetail;
import com.careerzip.domain.templatequestion.entity.TemplateQuestion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.careerzip.testobject.answer.AnswerFactory.createAnswerDetail;
import static com.careerzip.testobject.question.QuestionFactory.createCheckboxQuestion;
import static com.careerzip.testobject.templatequestion.TemplateQuestionFactory.createTemplateQuestionOf;
import static org.assertj.core.api.Assertions.assertThat;

class QuestionDetailTest {

    @Test
    @DisplayName("QuestionDetail 생성 테스트")
    void createQuestionDetailTest() {
        // given
        Question question = createCheckboxQuestion();
        TemplateQuestion templateQuestion = createTemplateQuestionOf(question);
        List<AnswerDetail> answers = Arrays.asList(createAnswerDetail(), createAnswerDetail(), createAnswerDetail());

        // when
        QuestionDetail questionDetail = QuestionDetail.of(templateQuestion, question, answers);
        List<AnswerOption> answerOptions = question.getAnswerOptions();

        // then
        assertThat(questionDetail.getAnswerOptions())
                .usingRecursiveComparison()
                .isEqualTo(answerOptions.stream()
                                        .map(AnswerOption::getDescription)
                                        .collect(Collectors.toList()));
        assertThat(questionDetail).hasNoNullFieldsOrProperties();
    }
}
