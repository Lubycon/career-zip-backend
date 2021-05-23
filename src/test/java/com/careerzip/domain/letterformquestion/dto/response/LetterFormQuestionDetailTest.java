package com.careerzip.domain.letterformquestion.dto.response;

import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.AnswerDetail;
import com.careerzip.domain.answeroption.AnswerOption;
import com.careerzip.domain.archiving.dto.response.archivingdetailresponse.QuestionDetail;
import com.careerzip.domain.letterformquestion.entity.LetterFormQuestion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.careerzip.testobject.answer.AnswerFactory.createAnswerDetail;
import static com.careerzip.testobject.question.QuestionFactory.createCheckboxQuestionTemplate;
import static com.careerzip.testobject.letterformquestion.LetterFormQuestionFactory.createLetterFormQuestionOf;
import static org.assertj.core.api.Assertions.assertThat;

class LetterFormQuestionDetailTest {

    @Test
    @DisplayName("QuestionDetail 생성 테스트")
    void createQuestionDetailTest() {
        // given
        Question question = createCheckboxQuestionTemplate();
        LetterFormQuestion letterFormQuestion = createLetterFormQuestionOf(question);
        List<AnswerDetail> answers = Arrays.asList(createAnswerDetail(), createAnswerDetail(), createAnswerDetail());

        // when
        QuestionDetail questionDetail = QuestionDetail.of(letterFormQuestion, answers);
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
