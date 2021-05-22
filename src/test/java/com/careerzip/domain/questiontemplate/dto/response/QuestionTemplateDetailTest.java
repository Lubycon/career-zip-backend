package com.careerzip.domain.questiontemplate.dto.response;

import com.careerzip.domain.questiontemplate.entity.QuestionTemplate;
import com.careerzip.domain.record.dto.response.recorddetailresponse.AnswerDetail;
import com.careerzip.domain.answeroption.AnswerOption;
import com.careerzip.domain.record.dto.response.recorddetailresponse.QuestionDetail;
import com.careerzip.domain.question.entity.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.careerzip.testobject.answer.AnswerFactory.createAnswerDetail;
import static com.careerzip.testobject.questiontemplate.QuestionTemplateFactory.createCheckboxQuestionTemplate;
import static com.careerzip.testobject.question.QuestionFactory.createQuestionOf;
import static org.assertj.core.api.Assertions.assertThat;

class QuestionTemplateDetailTest {

    @Test
    @DisplayName("QuestionDetail 생성 테스트")
    void createQuestionDetailTest() {
        // given
        QuestionTemplate questionTemplate = createCheckboxQuestionTemplate();
        Question question = createQuestionOf(questionTemplate);
        List<AnswerDetail> answers = Arrays.asList(createAnswerDetail(), createAnswerDetail(), createAnswerDetail());

        // when
        QuestionDetail questionDetail = QuestionDetail.of(question, answers);
        List<AnswerOption> answerOptions = questionTemplate.getAnswerOptions();

        // then
        assertThat(questionDetail.getAnswerOptions())
                .usingRecursiveComparison()
                .isEqualTo(answerOptions.stream()
                                        .map(AnswerOption::getDescription)
                                        .collect(Collectors.toList()));
        assertThat(questionDetail).hasNoNullFieldsOrProperties();
    }
}
