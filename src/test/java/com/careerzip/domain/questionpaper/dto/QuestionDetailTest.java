package com.careerzip.domain.questionpaper.dto;

import com.careerzip.domain.question.entity.Question;
import com.careerzip.domain.questionitem.entity.InputType;
import com.careerzip.domain.questionitem.entity.QuestionItem;
import com.careerzip.domain.selectoption.entity.SelectOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static com.careerzip.testobject.question.QuestionFactory.createQuestion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QuestionDetailTest {

    @Test
    @DisplayName("Question을 받아서 QuestionDetail을 생성하는 테스트")
    void questionDetailFromQuestionTest() {
        // given
        Question question = createQuestion();
        QuestionItem questionItem = question.getQuestionItem();
        InputType inputType = questionItem.getInputType();

        // when
        QuestionDetail questionDetail = QuestionDetail.from(question);

        // then
        assertThat(questionDetail).hasNoNullFieldsOrPropertiesExcept("example");
        assertThat(questionDetail.getInputType()).isEqualTo(inputType);
        assertThat(questionDetail.getSelectOptions()).containsAll(questionItem.getSelectOptions()
                                                                              .stream()
                                                                              .map(SelectOption::getDescription)
                                                                              .collect(Collectors.toList()));
    }
}
