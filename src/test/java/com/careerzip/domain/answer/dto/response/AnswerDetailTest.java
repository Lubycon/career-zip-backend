package com.careerzip.domain.answer.dto.response;

import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.hashtag.dto.response.HashtagDetail;
import com.careerzip.domain.hashtag.entity.Hashtag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.careerzip.testobject.answer.AnswerFactory.createAnswer;
import static com.careerzip.testobject.hashtag.HashtagFactory.createHashtag;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AnswerDetailTest {

    @Test
    @DisplayName("AnswerDetail 생성 테스트")
    void createAnswerDetailTest() {
        // given
        Answer answer = createAnswer();
        Hashtag hashtag = createHashtag();

        // when
        AnswerDetail answerDetail = AnswerDetail.of(answer, hashtag);
        HashtagDetail hashtagDetail = answerDetail.getHashtag();

        // then
        assertAll(
                () -> assertThat(answerDetail.getComment()).isEqualTo(answer.getComment()),
                () -> assertThat(hashtagDetail.getId()).isEqualTo(hashtag.getId()),
                () -> assertThat(hashtagDetail.getTitle()).isEqualTo(hashtag.getTitle())
        );
    }
}
