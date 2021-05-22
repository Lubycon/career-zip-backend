package com.careerzip.testobject.answer;

import com.careerzip.domain.record.dto.response.recorddetailresponse.AnswerDetail;
import com.careerzip.domain.answer.entity.Answer;
import com.careerzip.domain.hashtag.dto.response.HashtagDetail;
import com.careerzip.domain.hashtag.entity.Hashtag;

import static com.careerzip.testobject.hashtag.HashtagFactory.createHashtag;

public class AnswerFactory {

    // Answer
    public static Answer createAnswer() {
        return Answer.builder()
                     .id(1L)
                     .comment("Answer comment")
                     .hashtag(createHashtag())
                     .build();
    }


    // AnswerDetail
    public static AnswerDetail createAnswerDetail() {
        Answer answer = createAnswer();
        Hashtag hashtag = createHashtag();

        return AnswerDetail.builder()
                           .comment(answer.getComment())
                           .hashtag(HashtagDetail.from(hashtag))
                           .build();
    }
}
